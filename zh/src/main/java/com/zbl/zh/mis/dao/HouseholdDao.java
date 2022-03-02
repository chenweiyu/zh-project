package com.zbl.zh.mis.dao;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.mis.entity.Household;

import org.apache.ibatis.annotations.Param;

public interface HouseholdDao extends BaseMapper<Household> {

    public IPage<Household> getByIdAllSameCommunity(IPage<Map<String, Object>> page, @Param("map") Map<String, Object> params);
    
}