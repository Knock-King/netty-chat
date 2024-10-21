package com.king.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.king.model.po.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author King
 * @version 1.0
 * @description 用户信息mapper
 * @date 2024/10/21 15:46
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
