package com.zbl.zh.mis.dao;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.mis.entity.Electric;

import org.apache.ibatis.annotations.Param;

public interface ElectricDao extends BaseMapper<Electric> {
    
    public IPage<Electric> pageElectricMonth(IPage<Map<String, Object>> page, @Param("map") Map<String, Object> params);

}