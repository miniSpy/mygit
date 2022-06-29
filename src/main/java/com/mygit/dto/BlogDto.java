package com.mygit.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class BlogDto {


    private Long id;

    @NotNull(message = "作者id不能为空")
    private Long userId;

    @NotBlank(message = "標題不能爲空")
    private String title;

    @NotBlank(message = "描述不能爲空")
    private String description;

    @NotBlank(message = "内容不能爲空")
    private String content;

}
