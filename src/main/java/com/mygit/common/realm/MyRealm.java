package com.mygit.common.realm;

import com.mygit.common.filter.JwtToken;
import com.mygit.entity.MUser;
import com.mygit.service.MUserService;
import com.mygit.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private MUserService mUserService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * token是自定義的jwtToken
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        Claims claimByToken = jwtUtils.getClaimByToken(jwtToken.getPrincipal());
        String userId = claimByToken.getSubject();
        if(StringUtils.isEmpty(userId))
            throw new AuthorizationException("no this userId");
        MUser user = mUserService.getById(Long.parseLong(userId));
        if(ObjectUtils.isEmpty(user))
            throw new UnknownAccountException("no this user");
        if(user.getStatus() == -1){
            throw new LockedAccountException("user is locked");
        }
        AccountProfile context = new AccountProfile();
        BeanUtils.copyProperties(user,context);
        //传递用户相关信息给shiro
        return new SimpleAuthenticationInfo(context,token.getCredentials(),getName());
    }
}
