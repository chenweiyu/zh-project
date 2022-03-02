package com.zbl.zh.mis.dao;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.mis.entity.Householder;

import org.apache.ibatis.annotations.Param;

public interface HouseholderDao extends BaseMapper<Householder> {
    
    public IPage<Map<String, Object>> pageAllHouseholder(IPage<Map<String, Object>> page, @Param("map") Map<String, Object> params);

    public Map<String, Object> getByHolderId( @Param("map") Map<String, Object> params);
}
