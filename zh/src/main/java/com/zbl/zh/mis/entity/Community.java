package com.zbl.zh.mis.entity;

import java.sql.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@TableName("community")
public class Community {
    
    @TableId
    private Long id;

    private String communityName;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date communityYear;

    private int communityCoverArea;
    private int communityBuilding;
    private int communityUnit;
    private int communityHolderSum;
    private int communityPersonSum;
    private String communityOther;

}
