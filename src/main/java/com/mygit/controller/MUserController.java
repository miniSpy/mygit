package com.mygit.controller;


import com.mygit.common.ApiResult;
import com.mygit.entity.MUser;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ispy
 * @since 2022-05-13
 */
@RestController
@RequestMapping("/m-user")
public class MUserController {

    @RequestMapping("/save")
    public ApiResult saveUser(@Validated @RequestBody MUser user){
        return ApiResult.sendSuccess(user);
    }

}

