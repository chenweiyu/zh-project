package com.zbl.zh.mis.service.impl;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.zh.common.core.P;
import com.zbl.zh.mis.dao.HouseholdDao;
import com.zbl.zh.mis.entity.Household;
import com.zbl.zh.mis.service.HouseholdService;
import com.zbl.zh.mis.service.HouseholderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Service
public class HouseholdServiceImpl extends ServiceImpl<HouseholdDao, Household> implements HouseholdService{

    @Autowired
    HouseholderService householderService;

    @Override
    public IPage<Household> pageAllHousehold(Map<String, Object> params) {
        IPage<Household> result;
        QueryWrapper<Household> Q = new QueryWrapper<Household>();
        String query = (String) params.get("query");
        if (query != null && !query.isEmpty()) {
            Q.like("household_name", query);
        }
        result = this.page(P.page(params), Q);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveHousehold(Household household) {
        this.save(household);
        // String householdName = (String) params.get("householdName");
        // String householdPhone = (String) params.get("householdPhone");
        // String householdSex = (String) params.get("householdSex");
        // Date householdBirth = Date.valueOf((String) params.get("householdBirth"));
        // String householdIs = (String) params.get("householdIs");
        // String householderName = (String) params.get("householderName");
        // String householdRelation = (String) params.get("householdRelation");
        // String householdNativePlace = (String) params.get("householdNativePlace");
        // String householdIdentify = (String) params.get("householdIdentify");
        // String householdHjPlace = (String) params.get("householdHjPlace");
        // String householdOutlook = (String) params.get("householdOutlook");
        // String householdVeterans = (String) params.get("householdVeterans");
        // String householdSoldierFam = (String) params.get("householdSoldierFam");
        // String householdMartyrFam = (String) params.get("householdMartyrFam");
        

        // QueryWrapper<Householder> wrapper = new QueryWrapper<>();
        // wrapper.eq("householder_name", householderName);
        // List<Object> householderList =  householderService.listObjs(wrapper);
        // Long householderId = (long) householderList.get(0).hashCode();

        // Household household = new Household(householdName, householdPhone, householdSex, householdBirth, householdIs, householdRelation, householdNativePlace, householdIdentify, householdHjPlace, householdOutlook, householdVeterans, householdSoldierFam, householdMartyrFam, householderId);

        // this.save(household);
        
        // Household household = new Householder(householderName, householderNum, communityId, addressId);
        // this.save(householder);
        // wrapperHolderName.eq("householder_name", householderName);
        // List<Object> householderList = this.listObjs(wrapperHolderName);
        // Long householderId = (long) householderList.get(0).hashCode();
        // Electric electric = new Electric(householderId);
        // Water water = new Water(householderId);
        // electricService.save(electric);
        // waterService.save(water);
    }

    @Override
    public Household getByHousdeholdId(Long id) {
        Household result = this.getById(id);
        return result;
    }

    @Override
    public void updateHousehold(Household household) {
        this.updateById(household);
    }

    @Override
    public void removeByHouseholdIds(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public IPage<Household> getByIdSameCommunity(Map<String, Object> params) {
        IPage<Household> result = this.baseMapper.getByIdAllSameCommunity(P.page(params), params);
        return result;
    }
    
}