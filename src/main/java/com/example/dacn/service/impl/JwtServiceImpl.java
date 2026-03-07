package com.example.dacn.service.impl;

import com.example.dacn.db1.model.ChucVu;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.dacn.db1.model.viewmodel.NguoiDungView;
import com.example.dacn.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import static io.jsonwebtoken.Header.TYPE;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Example;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtServiceImpl implements JwtService<JwtService.ParamJwt> {

    Key key, keyRefresh;
    long expreTimeRefresh, expreTimeAccess;

    public JwtServiceImpl(
            @Value("${init-data.token-access.secret-key}") String key,
            @Value("${init-data.token-access.expire-date}") int expreTimeAccess,
            @Value("${init-data.token-refresh.expire-date}") int expreTimeRefresh,
            @Value("${init-data.token-refresh.secret-key}") String keyRefresh) {
        this.key = Keys.hmacShaKeyFor(Base64.encodeBase64(key.getBytes(), true));
        this.keyRefresh = Keys
                .hmacShaKeyFor(Base64.encodeBase64(keyRefresh.getBytes(), true));
        this.expreTimeRefresh = expreTimeRefresh * 60 * 1000l;
        this.expreTimeAccess = expreTimeAccess * 60 * 1000l;
    }

    @Override
    public String createJwt(ParamJwt paramJwt) {
        return switch (paramJwt.typeToken()) {
            case REFRESH ->
                createRefreshToken(paramJwt.nguoiDungView().getId());
            case ACCESS ->
                createAccessToken(paramJwt.nguoiDungView());
            default ->
                throw new IllegalArgumentException(
                        "Unexpected value: " + paramJwt.typeToken());

        };
    }

    @Override
    public Map<String, ?> getTokenProperties(String token) {
        return getClaimFromBody(token, Function.identity());
    }

    private String createRefreshToken(UUID id) {

        return createTokenBuider(keyRefresh,
                TypeToken.REFRESH, expreTimeRefresh).setId(id.toString()).compact();
    }

    private JwtBuilder createTokenBuider(Key key,
            TypeToken typeToken, long exp) {
        return Jwts.builder()
                .signWith(key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .setHeaderParam(TYPE, typeToken);
    }

    private String createAccessToken(NguoiDungView nguoiDung) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", nguoiDung.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList());
        claims.put("username", nguoiDung.getUsername());
        claims.put("storeName", nguoiDung.getStoreName());
        return createTokenBuider(key, TypeToken.ACCESS, expreTimeAccess)
                .setClaims(claims).compact();

    }

//    @Override
//    public Collection<? extends GrantedAuthority> getRoles(String jwt) {
//        // TODO Auto-generated method stub
//
//        return getClaimFromBody(jwt, claims -> {
//            List<?> lsRole = claims.get("roles", List.class);
//            return lsRole.stream().map(String.class::cast).
//                    map(ChucVu.RoleName::castStringToRole).toList();
//        });
//    }
    @Override
    public Jws<Claims> paseJwt(String jwt) {
        // TODO Auto-generated method stub
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(
                jwt);
    }

    private <T> T getClaimFromBody(String jwt, Function<Claims, T> function) {
        Claims claims = paseJwt(jwt).getBody();

        return function.apply(claims);
    }

    @Override
    public Header<?> getHeader(String jwt) {
        return paseJwt(jwt).getHeader();
    }

}
