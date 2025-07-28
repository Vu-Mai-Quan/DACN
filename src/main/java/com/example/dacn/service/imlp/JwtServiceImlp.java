/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.service.imlp;

import com.example.dacn.db1.model.TaiKhoan;
import com.example.dacn.db1.repositories.TaiKhoanRepo;
import com.example.dacn.db2.repositories.BlackListTokenRepo;
import com.example.dacn.service.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import java.security.Key;
import java.sql.Date;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author ADMIN
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImlp implements IJwtService {


    String seccretKey;

    long expireDate;

    long refreshExpireDate;
    Key key;
    String issure;
    TaiKhoanRepo khoanRepo;
    BlackListTokenRepo blackListTokenRepo;

    public JwtServiceImlp(
            @Value("${init-data.token.seccret-key}")  String seccretKey,
            @Value("${init-data.token.expire-date}")  long expireDate,
            @Value("${init-data.token-refresh.expire-date}")  long refreshExpireDate,
            @Value("${init-data.token.issuer}") String issure,
            TaiKhoanRepo khoanRepo,
            BlackListTokenRepo blackListTokenRepo) {
        this.seccretKey = seccretKey;
        this.expireDate = expireDate;
        this.refreshExpireDate = refreshExpireDate;
        this.issure = issure;
        this.khoanRepo = khoanRepo;
        this.blackListTokenRepo = blackListTokenRepo;
        this.key = Keys.hmacShaKeyFor(Base64.encodeBase64(this.seccretKey.getBytes(), true));
    }

    @Override
    public String createToken(UserDetails details) {
        return Jwts.builder()
                .claim("role", details.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer(issure)
                .setExpiration(new Date(System.currentTimeMillis() + expireDate))
                .setSubject(details.getUsername())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public UserDetails kiemTraTaiKhoanTrongToken(String token) {
        String sub = extractClaims(token, Claims::getSubject);
        return khoanRepo.timTaiKhoanTheoEmail(sub).orElseThrow(() -> new EntityNotFoundException("Tài khoản %s".formatted(sub)));

    }

    private Claims extractToken(String token) {
        try {
            if (blackListTokenRepo.findByToken(token) > 0) {
                throw new RuntimeException("Token đã bị khóa");
            } else {
                return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            }
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Token không được hỗ trợ");
        } catch (SignatureException e) {
            throw new SignatureException("Chữ kí không hợp lệ");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token đã hết hạn");
        }
    }

    private <T> T extractClaims(String token, Function<Claims, T> function) {
        return function.apply(extractToken(token));
    }

    @Override
    public String createRefreshToken(TaiKhoan details) {
        return Jwts.builder()
                .setId(details.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("refresh_token")
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpireDate))
                .setSubject(details.getUsername())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Override
    public boolean isRefreshToken(String token) {
        String getIssure = extractClaims(token, Claims::getIssuer);
        return getIssure.equals("refresh_token");
    }

    @Override
    public Date exprired(String token) {
        return new Date(extractClaims(token, Claims::getExpiration).getTime());
    }
}
