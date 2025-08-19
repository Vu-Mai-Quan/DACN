/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.imlp;

import com.example.dacn.basetemplate.dto.request.LoginDto;
import com.example.dacn.basetemplate.dto.request.PhanQuyenRq;
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
import com.example.dacn.db2.model.TokenRegister;
import com.example.dacn.db2.model.compositekey.IdRegisterToken;
import com.example.dacn.db2.repositories.TokenRegisterRepository;
import com.example.dacn.enumvalues.EnumRole;
import com.example.dacn.enumvalues.EnumTypeAccount;
import com.example.dacn.mapper.IMapperService;
import com.example.dacn.service.IAuthService;
import com.example.dacn.service.IJwtService;
import com.example.dacn.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    final TaiKhoanRepo taikhoanRepo;
    final IJwtService jwtSevice;
    final PasswordEncoder encode;
    final RefreshTokenRepo rfTkRp;
    final RoleRepo roleRepo;
    final ThongTinNDRepo thongTinNDRepo;
    IMapperService iMapperService;
    final AuthenticationManager authenticationManager;
    final TokenRegisterRepository tokenRegisterRepository;
    final MailService mailService;

    @Autowired
    public void setIMapperService(@Qualifier("ttndTKRoleMapper") final IMapperService iMapperService) {
        this.iMapperService = iMapperService;
    }

    @Override
    @Transactional(transactionManager = "db1TransactionManager", rollbackFor = Exception.class, label = "Login method")
    public LoginResponse login(LoginDto loginDto) {
//        TaiKhoan tk = taikhoanRepo.timTaiKhoanTheoEmail(loginDto.getUsername())
//                .orElseThrow(() -> new EntityNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác"));
//        if (!encode.matches(loginDto.getPassword(), tk.getPassword())) {
//            throw new EntityNotFoundException("Thông tin tài khoản hoặc mật khẩu không chính xác");
//        }
        var au = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        TaiKhoan tk = (TaiKhoan) au.getPrincipal();
        Optional<TokenAndExpriredView> rfRp = rfTkRp.getNewToken(tk.getId());
        String rf = rfRp.isPresent() && new Date(rfRp.get().getExprired()).after(new Date(System.currentTimeMillis())) ? rfRp.get().getToken() : insertRefreshToken(jwtSevice.createRefreshToken(tk), tk);

        String token = jwtSevice.createToken(Map.of("roles", au.getAuthorities()), tk);
        return LoginResponse.builder()
                .token(token)
                .refreshToken(rf)
                .tenTaiKhoan(tk.getUsername())
                .build();
    }

    @Override
    @Transactional(transactionManager = "db1TransactionManager", rollbackFor = Exception.class)
    public String dangKiTaiKhoan(RegisterDto registerDto) throws MessagingException {
        var ttnd = taoThongTinNguoiDung(registerDto);
        var tk = taoTaiKhoan(registerDto);
        tk.setThongTinNguoiDung(ttnd);
        ttnd.setTaiKhoan(tk);
        var idTk = thongTinNDRepo.save(ttnd).getId();
        var token = saveToken(idTk);
        mailService.sendSimpleMail(new String[]{tk.getUsername()}, "Xác thực tài khoản", token.getId().getIdUser(), token.getId().getToken());
        return "Đăng kí thành công";

    }

    @Transactional(transactionManager = "db2TransactionManager")
    protected TokenRegister saveToken(UUID id) {
        var tk = new TokenRegister();
        tk.setId(new IdRegisterToken(id, UUID.randomUUID().toString()));
        return tokenRegisterRepository.save(tk);
    }


    @Override
    @Transactional(transactionManager = "db1TransactionManager")
    public Set<TaiKhoanResponese> phanQuyenTaiKhoan(Set<PhanQuyenRq> quyenRq) {

        Set<UUID> uuids = quyenRq.stream().map(PhanQuyenRq::getId).collect(Collectors.toSet());

        Map<UUID, TaiKhoan> userMap = taikhoanRepo.findAllById(uuids).stream()
                .collect(Collectors.toMap(TaiKhoan::getId, u -> u));

        Map<String, Role> roleMap = roleRepo.findAllByRoleNames(EnumRole.getRoles()).stream()
                .collect(Collectors.toMap(item -> item.getRole().name(), Role::getInstance));

        for (var phanQuyenRq : quyenRq) {
            TaiKhoan tk = userMap.get(phanQuyenRq.getId());
            if (tk != null) {
                Set<Role> fullRoles = phanQuyenRq.getRoles().stream()
                        .map(item -> roleMap.get(item.name()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
                tk.setRoles(fullRoles);
            }
        }

        return getTaiKhoanResponese(taikhoanRepo.saveAll(userMap.values()));
    }

    @Override
    @Transactional("db1TransactionManager")
    public void kiemTraTokenDangKi(IdRegisterToken idRegisterToken) throws MessagingException {
        var token = tokenRegisterRepository.findByIdUserAndTokenOrderByTimeAsc(idRegisterToken).orElseThrow(() -> new EntityNotFoundException("Mã xác thực không hợp lệ"));
        if (token != null) {
            TaiKhoan taiKhoan = taikhoanRepo.findById(token.getId().getIdUser()).orElseThrow(() -> new EntityNotFoundException("Tài khoản không hợp lệ"));
            boolean conThoiHan = token.getTime().after(new Date(System.currentTimeMillis()));
            if (!conThoiHan) {
                var newToken = saveToken(taiKhoan.id);
                mailService.sendSimpleMail(new String[]{taiKhoan.getUsername()}, "Xác thực tài khoản", newToken.getId().getIdUser(), newToken.getId().getToken());
                throw new RuntimeException("Token đã hết hạn, chúng tôi đã gửi lại mã xác thực mới cho bạn");
            } else {
                taiKhoan.setCoBiKhoa(false);
                taiKhoan.setDaKichHoat(true);
                taikhoanRepo.save(taiKhoan);
            }

        }
    }


    private Set<TaiKhoanResponese> getTaiKhoanResponese(List<TaiKhoan> taiKhoans) {
        return taiKhoans.stream().map(item -> iMapperService.mapperObject(item, TaiKhoanResponese.class, taiKhoanToResponse())).collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private Consumer<ModelMapper> taiKhoanToResponse() {
        return (mapper) -> {
            try {
                TypeMap<TaiKhoan, TaiKhoanResponese> typeMap = mapper.getTypeMap(TaiKhoan.class, TaiKhoanResponese.class);
                if (typeMap == null) {
                    typeMap = mapper.createTypeMap(TaiKhoan.class, TaiKhoanResponese.class);
                    typeMap.addMappings(mapping -> {
                        mapping.using(ctx ->
                                ctx.getSource() == null ? Set.of() : ((Set<Role>) ctx.getSource()).stream().map(Role::getRole).collect(Collectors.toSet())
                        ).map(TaiKhoan::getRoles, TaiKhoanResponese::setRoleList);
                        mapping.map(TaiKhoan::getUsername, TaiKhoanResponese::setEmail);
                    });


                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi không thể chuyển đổi Set<Role> %s".formatted(e.getMessage()));
            }
        };

    }


    private TaiKhoan taoTaiKhoan(RegisterDto registerDto) {
        boolean email = taikhoanRepo.kiemTraEmailDaTonTai(registerDto.getEmail()) > 0;
        if (email) {
            throw new EntityExistsException("Tài khoản đã tồn tại");
        } else {
            return TaiKhoan.builder()
                    .email(registerDto.getEmail())
                    .matKhau(encode.encode(registerDto.getPassword()))
                    .roles(roleRepo.findAllByRole(EnumRole.CLIENT))
                    .type(EnumTypeAccount.CLIENT)
                    .coBiKhoa(true)
                    .daKichHoat(false)
                    .build();
        }

    }

    private ThongTinNguoiDung taoThongTinNguoiDung(RegisterDto registerDto) {
        var sdt = thongTinNDRepo.kiemTraSdtTonTai(registerDto.getSdt()) > 0;
        if (sdt) {
            throw new EntityExistsException("Số điện thoại đã tồn tại");
        }
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
