package com.zbl.zh.mis.entity;


import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@TableName(value = "electric")
public class Electric {
    
    @TableId
    private Long id;

    private Double electricPrice;
    private Double electricMoney;
    private Double electricNum;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date electricDate;
    private Long householderId;

    @TableField(exist = false)
	private String householderName;

    public Electric() {

    }

    public Electric(Long householderId) {
        this.householderId = householderId;
    }

    public Electric(Double electricPrice2, Double electricMoney2, Double electricNum2, Date electricDate2, Long householderId) {
        this.electricPrice = electricPrice2;
        this.electricMoney = electricMoney2;
        this.electricNum = electricNum2;
        this.electricDate = electricDate2;
        this.householderId = householderId;
    }

    
}
