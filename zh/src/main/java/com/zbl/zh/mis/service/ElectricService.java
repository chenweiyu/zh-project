package com.zbl.zh.mis.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.zh.mis.entity.Electric;

public interface ElectricService extends IService<Electric> {
    
    public IPage<Electric> pageElectricMonth(Map<String, Object> params);

    public void saveElectric(Map<String, Object> params);

    public Map<String, Object> getByElectricId(Long id);

    public void updateElectric(Map<String, Object> params);

    public void removeByElectricIds(Long[] ids);

}