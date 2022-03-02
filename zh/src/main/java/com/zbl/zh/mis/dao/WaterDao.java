package com.zbl.zh.mis.dao;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.mis.entity.Water;

import org.apache.ibatis.annotations.Param;

public interface WaterDao extends BaseMapper<Water> {
    
    public IPage<Water> pageWaterMonth(IPage<Map<String, Object>> page, @Param("map") Map<String, Object> params);

}