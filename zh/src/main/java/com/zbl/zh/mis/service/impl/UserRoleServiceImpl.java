package com.zbl.zh.mis.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.zh.mis.dao.UserRoleDao;
import com.zbl.zh.mis.entity.Role;
import com.zbl.zh.mis.entity.User;
import com.zbl.zh.mis.entity.UserRole;
import com.zbl.zh.mis.service.UserRoleService;

import org.springframework.stereotype.Service;

/**
 *  extends ServiceImpl
 * 
 *   
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    /**
     * 根据用户ID，查询拥有的所有角色
     */
    @Override
    public List<Role> getRoleByUserId(Long userId) {
        return this.baseMapper.getRoleByUserId(userId);
    }
    
    /**
     * 根据角色ID，查询分配到的所有用户
     */
    @Override
    public List<User> getUserByRoleId(Long roleId) {
        return this.baseMapper.getUserByRoleId(roleId);
    }
   
}
