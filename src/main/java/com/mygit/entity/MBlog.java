package com.mygit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author ispy
 * @since 2022-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "文章id不能爲空")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    @NotBlank(message = "標題不能爲空")
    private String title;

    @NotBlank(message = "描述不能爲空")
    private String description;

    @NotBlank(message = "内容不能爲空")
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;

    private Integer status;


}
