package com.zbl.zh.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName(value = "community_expand")
public class CommunityExpand {
    
    @TableId
    private Long id;
    private Double ceOccupancy;
    private Double ceUnitRate;
    private Double ceVaccinationRate;
    private String ceOther;
    private Long communityId;

    public CommunityExpand() {

    }

    public CommunityExpand(Long communityExpId, Long communityId) {
        this.id = communityExpId;
        this.communityId = communityId;
    }
}