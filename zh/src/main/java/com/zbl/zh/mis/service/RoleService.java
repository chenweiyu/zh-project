package com.zbl.zh.mis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.zh.mis.entity.Role;


/**
 * extends IService
 */
public interface RoleService extends IService<Role>{
    

    public  void savetransfer(Long rid,Long[] uids);

    /**
    * 删除用户和角色
    */
    public void deleteUserAndRole(Long[] ids);
}
