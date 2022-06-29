package com.mygit.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    /**
     *
     */
    @Value("${token.jwtSecret}")
    private String secret="(I:)_$^11244^%$_(S:)_@@++--(P:)_++++_.sds_(Y:)";

    @Value("${token.expire.seconds}")
    private long expire=7200;

    @Value("${token.header}")
    private String header="Authorization";


    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        Date now = new Date();
        //過期時間
        Date expireDate = new Date(now.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(userId+"")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("userid",userId)
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    // 获取jwt的信息
    public Claims getClaimByToken(String token) {
        try {
            return  Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

        }catch (Exception e){
            log.debug("token is error",e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
