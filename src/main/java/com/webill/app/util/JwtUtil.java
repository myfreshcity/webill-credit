package com.webill.app.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {
    public static String sercetKey = "mmh@2017Yii";
    public final static long keeptime = 1800000;

    public static String generToken(String id, String issuer, String subject) {
        long ttlMillis = keeptime;
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(sercetKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now);
        if (subject != null) {
            builder.setSubject(subject);
        }
        if (issuer != null) {
            builder.setIssuer(issuer);
        }
        builder.signWith(signatureAlgorithm, signingKey);

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public String updateToken(String token) {
        try {
            Claims claims = verifyToken(token);
            if (claims != null) {
            	String id = claims.getId();
            	String subject = claims.getSubject();
            	String issuer = claims.getIssuer();
            	Date date = claims.getExpiration();
            	return generToken(id, issuer, subject);
			}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }


    public String updateTokenBase64Code( Claims claims) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //BASE64Decoder decoder = new BASE64Decoder();
        try {
            //token = new String(decoder.decodeBuffer(token), "utf-8");
            // Claims claims= verifyToken(token);
            String id = claims.getId();
            String subject = claims.getSubject();
            String issuer = claims.getIssuer();
            Date date = claims.getExpiration();
            String newToken = generToken(id, issuer, subject);
            return newToken;
            //return base64Encoder.encode(newToken.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }


    public Claims verifyToken(String token) {
    	try {
    		Claims claims = Jwts.parser()
    				.setSigningKey(DatatypeConverter.parseBase64Binary(sercetKey))
    				.parseClaimsJws(token).getBody();
    		Date date = claims.getExpiration();
    		long offset = date.getTime()-System.currentTimeMillis();
    		if(offset >= 0){
    			return claims;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
    }

	public String getUserId(String token) {
    	//BASE64Decoder decoder = new BASE64Decoder();
    	try {
    		//byte[] decodeBuffer = decoder.decodeBuffer(token);
    		//String tokenMain = new String(decodeBuffer);
    		
    		Claims claims = Jwts.parser()
    				.setSigningKey(DatatypeConverter.parseBase64Binary(sercetKey))
    				.parseClaimsJws(token).getBody();
    		if(claims.getSubject().equals("uid")){
    			return claims.getId();
    		}else{
    			return null;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "0";
    }
}
