package com.zbl.zh.mis.entity;


import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@TableName(value = "water")
public class Water {
    
    @TableId
    private Long id;

    private Double waterPrice;
    private Double waterMoney;
    private Double waterNum;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date waterDate;
    private Long householderId;

    @TableField(exist = false)
	private String householderName;

    public Water() {
    }

    public Water(Long householderId2) {
        this.householderId = householderId2;
    }

    public Water(Double waterPrice2, Double waterMoney2, Double waterNum2, Date waterDate2, Long householderId) {
        this.waterPrice = waterPrice2;
        this.waterMoney = waterMoney2;
        this.waterNum = waterNum2;
        this.waterDate = waterDate2;
        this.householderId = householderId;
    }

}
