package com.mygit.common.realm;

import lombok.Data;

import java.io.Serializable;

/**
 * 登錄態信息
 * @author ispy
 * @date 2021/2/2
 */
@Data
public class AccountProfile implements Serializable {

    private String username;

    private Long id;

    private String avatar;
}
