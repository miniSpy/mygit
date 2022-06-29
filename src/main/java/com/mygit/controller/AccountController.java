package com.mygit.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mygit.common.ApiResult;
import com.mygit.dto.LoginDto;
import com.mygit.entity.MUser;
import com.mygit.service.MUserService;
import com.mygit.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@Slf4j
public class AccountController {

    @Autowired
    private MUserService mUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ApiResult login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        //全局異常捕獲參數異常
        MUser user = mUserService.getOne(new QueryWrapper<MUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(user,"用戶不存在");
        log.info("username:{},password:{}",user.getUsername(),user.getPassword());
//        if(!(loginDto.getPassword().equals(SecureUtil.md5(user.getPassword())))){
        if(!loginDto.getPassword().equals(user.getPassword())){
            return ApiResult.sendFail("密碼錯誤");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization",jwt);
        //响应首部 Access-Control-Expose-Headers 用来配置哪些首部可以作为响应的一部分暴露给外部。
        //暴露给前端
        response.setHeader("Access-Control-Expose-Headers","Authorization");

        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId",user.getId());
        userInfo.put("userName",user.getUsername());
        userInfo.put("userAvatar",user.getAvatar());
        return ApiResult.sendSuccess(userInfo);

        //放棄使用HUtools 會出現序列化問題
        // https://wenku.baidu.com/view/42fffe0cb7daa58da0116c175f0e7cd185251851.html
//        return ApiResult.sendSuccess(MapUtil.builder()
//                .put("userId",user.getId().toString())
//                .put("userName",user.getUsername())
//                .put("userAvatar",user.getAvatar()))
//                ;
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public ApiResult logout(){
        //shiro登出
        log.info("com.mygit.controller.AccountController.logout退出登录");
        SecurityUtils.getSubject().logout();
        return ApiResult.sendSuccess(null);
    }
}
