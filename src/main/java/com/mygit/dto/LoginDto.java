package com.mygit.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登錄dto
 * @author ispy
 * @2021/2/2
 */
@Data
public class LoginDto implements Serializable {

    /**
     * 用戶名
     */
    private String username;

    /**
     * 密碼
     */
    private String password;
}
