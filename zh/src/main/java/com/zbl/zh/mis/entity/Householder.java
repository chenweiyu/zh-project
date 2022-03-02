package com.zbl.zh.mis.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "householder")
public class Householder implements Serializable{
    @TableId
    private Long id;
    private String householderName;
    private int householderNum;
    private Long addressId;
    private Long communityId;
    private Long householdId;

    public Householder() {

    }
    
    public Householder(String householderName, int householderNum, Long communityId, Long addressId) {
        this.householderName = householderName;
        this.householderNum = householderNum;
        this.addressId = addressId;
        this.communityId = communityId;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseholderName() {
        return this.householderName;
    }

    public void setHouseholderName(String householderName) {
        this.householderName = householderName;
    }

    public int getHouseholderNum() {
        return this.householderNum;
    }

    public void setHouseholderNum(int householderNum) {
        this.householderNum = householderNum;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCommunityId() {
        return this.communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }


    public Long getHouseholdId() {
        return this.householdId;
    }

    public void setHouseholdId(Long householdId) {
        this.householdId = householdId;
    }

    
}