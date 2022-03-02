package com.zbl.zh.mis.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 角色
 * 
 */

@TableName("role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;
	private String roleName;
	private String rolePerms;
	private String remark;
	 
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRolePerms(String rolePerms) {
		this.rolePerms = rolePerms;
	}
	public String getRolePerms() {
		return rolePerms;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark() {
		return remark;
	}
}