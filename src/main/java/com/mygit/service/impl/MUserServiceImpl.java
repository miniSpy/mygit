package com.mygit.service.impl;

import com.mygit.entity.MUser;
import com.mygit.mapper.MUserMapper;
import com.mygit.service.MUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ispy
 * @since 2022-05-13
 */
@Service
public class MUserServiceImpl extends ServiceImpl<MUserMapper, MUser> implements MUserService {

}
