/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.imlp;

import com.example.dacn.basetemplate.dto.request.LoginDto;
import com.example.dacn.basetemplate.dto.request.RegisterDto;
import com.example.dacn.basetemplate.dto.response.LoginResponse;
import com.example.dacn.basetemplate.dto.response.TaiKhoanResponese;
import com.example.dacn.basetemplate.dto.response.TokenAndExpriredView;
import com.example.dacn.db1.model.RefreshToken;
import com.example.dacn.db1.model.Role;
import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db1.model.ThongTinNguoiDung;
import com.example.dacn.db1.repositories.RefreshTokenRepo;
import com.example.dacn.db1.repositories.RoleRepo;
import com.example.dacn.db1.repositories.TaiKhoanRepo;
import com.example.dacn.db1.repositories.ThongTinNDRepo;
import com.example.dacn.enumvalues.EnumRole;
import com.example.dacn.enumvalues.EnumTypeAccount;
import com.example.dacn.mapper.IMapperService;
import com.example.dacn.service.IAuthService;
import com.example.dacn.service.IJwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthServiceImlp implements IAuthService {

    TaiKhoanRepo taikhoanRepo;
    IJwtService jwtSevice;
    PasswordEncoder encode;
    RefreshTokenRepo rfTkRp;
    RoleRepo roleRepo;
    ThongTinNDRepo thongTinNDRepo;
    IMapperService iMapperService;

    @Override
    @Transactional(transactionManager = "db2TransactionManager")
    public LoginResponse login(LoginDto loginDto) {
        TaiKhoan tk = taikhoanRepo.timTaiKhoanTheoEmail(loginDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác"));
        if (!encode.matches(loginDto.getPassword(), tk.getPassword())) {
            throw new EntityNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác");
        }
        Optional<TokenAndExpriredView> rfRp = rfTkRp.getNewToken(tk.getId());
        String rf = rfRp.isPresent() && new Date(rfRp.get().getExprired()).after(new Date(System.currentTimeMillis())) ? rfRp.get().getToken() : insertRefreshToken(jwtSevice.createRefreshToken(tk), tk);
        String token = jwtSevice.createToken(tk);
        return LoginResponse.builder()
                .token(token)
                .refreshToken(rf)
                .tenTaiKhoan(tk.getUsername())
                .build();
    }

    @Override
    @Transactional(transactionManager = "db1TransactionManager")
    public String dangKiTaiKhoan(RegisterDto registerDto) {
        var tk = taoTaiKhoan(registerDto);
        var ttnd = taoThongTinNguoiDung(registerDto);
        tk.setThongTinNguoiDung(ttnd);
        ttnd.setTaiKhoan(tk);
        thongTinNDRepo.save(ttnd);
        return "Đăng kí thành công";

    }

    @Override
    public TaiKhoanResponese phanQuyenTaiKhoan(UUID idTaiKhoan, Set<Role> roles) {
      var tk =  taikhoanRepo.findById(idTaiKhoan).orElseThrow(EntityNotFoundException::new);
      tk.setRoles(roles);
      taikhoanRepo.save(tk);

      iMapperService.mapperObject(tk, TaiKhoanResponese.class, (mapper)->{
        var typeMap =  mapper.getTypeMap(TaiKhoan.class, TaiKhoanResponese.class);
        if (typeMap == null){
            typeMap = mapper.createTypeMap(TaiKhoan.class, TaiKhoanResponese.class);
            typeMap.addMappings(map->{
                map.using(ctx->{
                    return ctx;
                });
            });
        }
      });
      return null;
    }

    private TaiKhoan taoTaiKhoan(RegisterDto registerDto) {
        boolean email = taikhoanRepo.kiemTraEmailDaTonTai(registerDto.getEmail()) > 0,
                sdt = thongTinNDRepo.kiemTraSdtTonTai(registerDto.getSdt()) > 0;
        if (email) {
            throw new EntityExistsException("Tài khoản đã tồn tại");
        } else if (sdt) {
            throw new EntityExistsException("Số điện thoại đã tồn tại");
        } else {
            return TaiKhoan.builder()
                    .email(registerDto.getEmail())
                    .matKhau(encode.encode(registerDto.getPassword()))
                    .roles(roleRepo.findAllByRole(EnumRole.CLIENT))
                    .type(EnumTypeAccount.CLIENT)
                    .build();
        }

    }

    private ThongTinNguoiDung taoThongTinNguoiDung(RegisterDto registerDto) {

        return ThongTinNguoiDung.builder()
                .sdt(registerDto.getSdt())
                .avatar("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vecteezy.com%2Ffree-vector%2Fanonymous-avatar&psig=AOvVaw1TrFmri1189c5VYhFz0eCT&ust=1754481236893000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCNi2hZnO844DFQAAAAAdAAAAABAE")
                .hoTen("No name user")
                .build();
    }

    private String insertRefreshToken(String token, TaiKhoan idTaiKhoan) {
        return rfTkRp.save(new RefreshToken(idTaiKhoan, jwtSevice.exprired(token).getTime(), token)).getToken();
    }

}
