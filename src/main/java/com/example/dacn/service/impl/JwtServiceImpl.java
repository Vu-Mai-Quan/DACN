package com.example.dacn.service.impl;

import java.security.Key;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.dacn.db1.model.NguoiDung;
import com.example.dacn.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class JwtServiceImpl implements JwtService {

	Key key, keyRefresh;
	long expreTimeRefresh, expreTimeAccess;

	public JwtServiceImpl(@Value("${init-data.token.secret-key-access}") String key,
			@Value("${init-data.token.expire-date}") int expreTimeAccess,
			@Value("${init-data.token-refresh.expire-date}") int expreTimeRefresh,
			@Value("${init-data.token.secret-key-refresh}") String keyRefresh) {
		this.key = Keys.hmacShaKeyFor(Base64.encodeBase64(key.getBytes(), true));
		this.keyRefresh = Keys
				.hmacShaKeyFor(Base64.encodeBase64(keyRefresh.getBytes(), true));
		this.expreTimeRefresh = expreTimeRefresh * 60 * 1000l;
		this.expreTimeAccess = expreTimeAccess * 60 * 1000l;
	}

	@Override
	public String createJwt(ParamJwt jwt) {
		return switch (jwt.type()) {

		case ACCESS -> createAccessToken(jwt.nguoiDung());
		case REFRESH -> createRefreshToken(jwt.nguoiDung());
		default -> throw new IllegalArgumentException("Unexpected value: " + jwt.type());

		};
	}

	private String createRefreshToken(NguoiDung nguoiDung) {
		return createTokenBuider(keyRefresh, expreTimeRefresh,
				nguoiDung.getId().toString(), nguoiDung.getUsername()).compact();
	}

	private JwtBuilder createTokenBuider(Key key, long time, String subj, String isIss) {
		return Jwts.builder().signWith(key).setSubject(subj)
				.setExpiration(Date.from(Instant.now()))
				.setIssuedAt(new Date(System.currentTimeMillis() + time))
				.setIssuer(isIss);
	}

	private String createAccessToken(NguoiDung nguoiDung) {

		return createTokenBuider(key, expreTimeAccess, nguoiDung.getId().toString(),
				nguoiDung.getUsername())
				.claim("roles", nguoiDung.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).toList())
				.compact();

	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String jwt) {
		// TODO Auto-generated method stub

		return getClaimFromBody(jwt, claims -> ((List<String>) claims.get("roles"))
				.stream().map(SimpleGrantedAuthority::new).toList());
	}

	private Claims paseBodyJwt(String jwt) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt)
				.getBody();
	}

	private <T> T getClaimFromBody(String jwt, Function<Claims, T> function) {
		Claims claims = paseBodyJwt(jwt);
		return function.apply(claims);
	}

}
