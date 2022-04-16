package com.spring.baitap10.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.spring.baitap10.security.userprincal.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component //khi spring chay thi load qua class nay
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	private String jwtSecret = "dai@gmail.com";
	private int jwtExpiration = 86400;
	//tạo ra chuỗi token khi đăng nhập
	public String createToken(Authentication auth) {
		UserPrinciple userPrinciple = (UserPrinciple) auth.getPrincipal();
		return Jwts.builder()
				.setSubject(userPrinciple.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration*1000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	//kiểm tra token có hợp lệ hay không
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token); //truyền vào key và lấy ra chuỗi token
			return true;
		} catch (SignatureException e) {  //đăng kí key không hợp lệ
			logger.info("Invalid JWT signature.");
			logger.trace("Invalid JWT signature trace: {}", e);
		    } catch (MalformedJwtException e) {  //token không đúng định dạng
		    	logger.info("Invalid JWT token.");
		    	logger.trace("Invalid JWT token trace: {}", e);
		    } catch (ExpiredJwtException e) {  //token chết
		    	logger.info("Expired JWT token.");
		    	logger.trace("Expired JWT token trace: {}", e);
		    } catch (UnsupportedJwtException e) { //token không support
		    	logger.info("Unsupported JWT token.");
		    	logger.trace("Unsupported JWT token trace: {}", e);
		    } catch (IllegalArgumentException e) {  //token có chứa khoảng trắng
		    	logger.info("JWT token compact of handler are invalid.");
		    	logger.trace("JWT token compact of handler are invalid trace: {}", e);
		    }
		return false;
	}
	public String getUsernameFromToken(String token) {
		String username = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return username;
	}
}
