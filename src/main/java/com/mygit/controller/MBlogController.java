package com.mygit.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mygit.common.ApiResult;
import com.mygit.common.realm.AccountProfile;
import com.mygit.dto.BlogDto;
import com.mygit.entity.MBlog;
import com.mygit.service.MBlogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ispy
 * @since 2022-05-13
 */
@RestController
@RequestMapping("/m-blog")
@Slf4j
public class MBlogController {

    @Autowired
    private MBlogService mBlogService;

    @GetMapping("/list")
    public ApiResult blogList(@RequestParam( value = "page",defaultValue = "1" )Integer page){
        IPage iPage = new Page(page,5);
        IPage allBlog = mBlogService.page(iPage,new QueryWrapper<MBlog>().orderByDesc("created"));
        log.info("allBlog:{}",allBlog);

        return ApiResult.sendSuccess(allBlog);
    }

    /**
     * 查詢執行文章
     */
    @GetMapping("/list/{id}")
    public ApiResult findBlogById(@PathVariable(name = "id") Long id){
        Assert.notNull(id,"文章id不能爲空");
        MBlog blog = mBlogService.getById(id);
        log.info("com.mygit.controller.MBlogController.findBlogById.blog:{}",blog);
        if(ObjectUtils.isEmpty(blog)){
            return ApiResult.sendFail("文章不存在");
        }
        return ApiResult.sendSuccess(blog);
    }

    /**
     * 編輯文章
     */
    @RequiresAuthentication
    @PostMapping("/edit")
    public ApiResult editBlog(@Validated @RequestBody BlogDto blogDto){
        log.info("com.mygit.controller.MBlogController.editBlog.param:{}",blogDto);
        MBlog mBlog = new MBlog();
        if(!ObjectUtils.isEmpty(blogDto.getId())){
            MBlog blogById = mBlogService.getById(blogDto.getId());
            log.info("com.mygit.controller.MBlogController.editBlog.blogById:{}",blogById);
            Assert.notNull(blogById.getId(),"不存在此文章");
            if(blogById.getStatus() == null || blogById.getStatus() == -1)
                return ApiResult.sendFail("文章禁止編輯");
            AccountProfile principal = (AccountProfile) SecurityUtils.getSubject().getPrincipal();
            if(principal.getId().longValue() != blogById.getUserId().longValue()){
                return ApiResult.sendFail("不能編輯不是本人的文章");
            }
        }
        else{
            //新文章設置創建時間
            mBlog.setCreated(new Date());
            mBlog.setStatus(0);
        }
        BeanUtils.copyProperties(blogDto,mBlog);
        log.info("com.mygit.controller.MBlogController.editBlog.mBlog:{}",mBlog);
        mBlogService.saveOrUpdate(mBlog);
        return ApiResult.sendSuccess(200,"操作成功",null);

    }


    /**
     * 测试用
     * @return
     */
    @RequiresAuthentication
    @RequestMapping("/index")
    public ApiResult getIndex(){
        MBlog res = mBlogService.getById(1);
        if(ObjectUtils.isEmpty(res)){
            return ApiResult.sendSuccess(200,"数据为空", res);
        }

        return ApiResult.sendSuccess(res);
    }



}

