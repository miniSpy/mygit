package com.mygit.common.filter;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @jwt結構
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt){
        this.token=jwt;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public String getCredentials() {
        return token;
    }
}
