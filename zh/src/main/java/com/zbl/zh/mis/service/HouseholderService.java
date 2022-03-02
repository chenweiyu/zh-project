package com.zbl.zh.mis.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.zh.mis.entity.Householder;

public interface HouseholderService extends IService<Householder> {
    public IPage<Map<String, Object>> pageAllHouseholder(Map<String, Object> params);

    public void saveHouseholder(Map<String, Object> params);

    public Map<String, Object> getByHousdeholderId(Long id);

    public void updateHouseholder(Map<String, Object> params);

    public void removeByHouseholderIds(Long[] ids);
}