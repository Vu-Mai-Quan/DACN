package com.example.dacn.service.impl;

import static io.jsonwebtoken.Header.TYPE;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.dacn.db1.model.NguoiDung;
import com.example.dacn.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class JwtServiceImpl implements JwtService<JwtService.ParamJwt<NguoiDung>> {

	Key key, keyRefresh;
	long expireTimeRefresh, expireTimeAccess;

	public JwtServiceImpl(@Value("${init-data.token-access.secret-key}") String key,
			@Value("${init-data.token-access.expire-date}") int expirationTimeAccess,
			@Value("${init-data.token-refresh.expire-date}") int expireTimeRefresh,
			@Value("${init-data.token-refresh.secret-key}") String keyRefresh) {
		this.key = Keys.hmacShaKeyFor(Base64.encodeBase64(key.getBytes(), true));
		this.keyRefresh = Keys
				.hmacShaKeyFor(Base64.encodeBase64(keyRefresh.getBytes(), true));
		this.expireTimeRefresh = expireTimeRefresh * 60 * 1000L;
		this.expireTimeAccess = expirationTimeAccess * 60 * 1000L;
	}

	@Override
	public String createJwt(ParamJwt<NguoiDung> paramJwt) {
		return switch (paramJwt.typeToken()) {
		case REFRESH -> createRefreshToken(paramJwt.param().getId().toString());
		case ACCESS -> createAccessToken(paramJwt.param());
		};
	}

	@Override
	public Map<String, ?> getTokenProperties(String token) {
		return getClaimFromBody(token, Function.identity());
	}

	private String createRefreshToken(String id) {
		return createTokenBuilder(keyRefresh, TypeToken.REFRESH, expireTimeRefresh)
				.setId(id).compact();
	}

	private JwtBuilder createTokenBuilder(Key key, TypeToken typeToken, long exp) {
		return Jwts.builder().signWith(key)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + exp))

				.setHeaderParam(TYPE, typeToken);
	}

	private String createAccessToken(NguoiDung nguoiDung) {
		var claims = Map.of("roles", nguoiDung.getAuthorities(),
				"username", nguoiDung.getUsername());
		return createTokenBuilder(key, TypeToken.ACCESS, expireTimeAccess)
				.addClaims(claims).setSubject(String.valueOf(nguoiDung.getId()))
				.compact();

	}

	// @Override
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
	public Jws<Claims> paseJwt(String jwt) throws JwtException {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
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
