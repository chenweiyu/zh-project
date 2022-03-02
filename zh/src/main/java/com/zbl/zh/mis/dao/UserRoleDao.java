package com.zbl.zh.mis.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbl.zh.mis.entity.Role;
import com.zbl.zh.mis.entity.User;
import com.zbl.zh.mis.entity.UserRole;


public interface UserRoleDao extends BaseMapper<UserRole> {

     /**
      * 根据用户ID，查询拥有的所有角色
      * @param userId
      * @return
      */

    public List<Role>  getRoleByUserId(Long  userId);
     

    /**
     * 根据角色ID，查询分配到的所有用户
     * @param roleId
     * @return
     */
    public List<User>  getUserByRoleId(Long roleId);
    
}
