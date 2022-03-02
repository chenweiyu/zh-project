package com.zbl.zh.mis.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.zh.mis.entity.Household;

public interface HouseholdService extends IService<Household> {
    public IPage<Household> pageAllHousehold(Map<String, Object> params);
    public void saveHousehold(Household household);

    public Household getByHousdeholdId(Long id);

    public void updateHousehold(Household household);

    public void removeByHouseholdIds(Long[] ids);

    public IPage<Household> getByIdSameCommunity(Map<String, Object> params);
}