package com.zbl.zh.mis.entity;

import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@TableName("household")
public class Household {
    @TableId
    private Long id;
    
    private String householdName;
    private String householdPhone;
    private String householdIdentify;
    private String householdSex;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date householdBirth;

    private String householdIs;
    private String householdRelation;
    private String householdNativePlace;
    private String householdHjPlace;
    private String householdOutlook;
    private String householdVeterans;
    private String householdSoldierFam;
    private String householdMartyrFam;
    private Long householderId;
    private Long communityId;

    public Household() {
    }

    public Household(String householdName, String householdPhone, String householdSex, Date householdBirth, String householdIs, String householdRelation, String householdNativePlace, String householdIdentify, String householdHjPlace, String householdOutlook, String householdVeterans, String householdSoldierFam, String householdMartyrFam, Long householderId, Long communityId) {
        this.householdName = householdName;
        this.householdPhone = householdPhone;
        this.householdSex = householdSex;
        this.householdBirth = householdBirth;
        this.householdIs = householdIs;
        this.householdRelation = householdRelation;
        this.householdNativePlace = householdNativePlace;
        this.householdIdentify = householdIdentify;
        this.householdHjPlace = householdHjPlace;
        this.householdOutlook = householdOutlook;
        this.householdVeterans = householdVeterans;
        this.householdSoldierFam = householdSoldierFam;
        this.householdMartyrFam = householdMartyrFam;
        this.householderId = householderId;
        this.communityId = communityId;
    }


}
