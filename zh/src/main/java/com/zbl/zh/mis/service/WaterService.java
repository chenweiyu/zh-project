package com.zbl.zh.mis.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.zh.mis.entity.Water;

public interface WaterService extends IService<Water> {
    
    public IPage<Water> pageWaterMonth(Map<String, Object> params);

    public void saveWater(Map<String, Object> params);

    public Map<String, Object> getByWaterId(Long id);

    public void updateWater(Map<String, Object> params);

    public void removeByWaterIds(Long[] ids);

}