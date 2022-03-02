package com.zbl.zh.mis.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.zh.common.core.P;
import com.zbl.zh.mis.dao.HouseholderDao;
import com.zbl.zh.mis.entity.Address;
import com.zbl.zh.mis.entity.Community;
import com.zbl.zh.mis.entity.Electric;
import com.zbl.zh.mis.entity.Householder;
import com.zbl.zh.mis.entity.Water;
import com.zbl.zh.mis.service.AddressService;
import com.zbl.zh.mis.service.CommunityService;
import com.zbl.zh.mis.service.ElectricService;
import com.zbl.zh.mis.service.HouseholderService;
import com.zbl.zh.mis.service.WaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Service
public class HouseholderServiceImpl extends ServiceImpl<HouseholderDao, Householder> implements HouseholderService{

    @Autowired
    AddressService addressService;
    @Autowired
    CommunityService communityService;
    @Autowired
    ElectricService electricService;
    @Autowired
    WaterService waterService;

    @Override
    public IPage<Map<String, Object>> pageAllHouseholder(Map<String, Object> params) {
        IPage<Map<String, Object>> result;
        String householderCommunity = (String) params.get("householderCommunity");
        if (householderCommunity.equals("所有社区")){
            params.put("householderCommunity", "");
        }
        result = this.baseMapper.pageAllHouseholder(P.page(params), params);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveHouseholder(Map<String, Object> params) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        QueryWrapper<Community> wrapperCommunity = new QueryWrapper<>();
        QueryWrapper<Householder> wrapperHolderName = new QueryWrapper<>();
        String householderName = (String) params.get("householderName");
        int householderNum = Integer.parseInt((String) params.get("householderNum"));
        String householderCommunity = (String) params.get("householderCommunity");
        String householderStreet = (String) params.get("householderStreet");
        String householderUnit = (String) params.get("householderUnit");
        String householderHouseNum = (String) params.get("householderHouseNum");
        wrapperCommunity.eq("community_name", householderCommunity);
        List<Object> communityList = communityService.listObjs(wrapperCommunity);
        Long communityId = (long) communityList.get(0).hashCode();
        Address address = new Address(householderStreet, householderUnit, householderHouseNum);
        addressService.save(address);
        wrapper.eq("householder_street", householderStreet).eq("householder_unit", householderUnit).eq("householder_house_num", householderHouseNum);
        List<Object> addressList = addressService.listObjs(wrapper);
        Long addressId = (long) addressList.get(0).hashCode();
        Householder householder = new Householder(householderName, householderNum, communityId, addressId);
        this.save(householder);
        wrapperHolderName.eq("householder_name", householderName);
        List<Object> householderList = this.listObjs(wrapperHolderName);
        Long householderId = (long) householderList.get(0).hashCode();
        Electric electric = new Electric(householderId);
        Water water = new Water(householderId);
        electricService.save(electric);
        waterService.save(water);
    }

    @Override
    public Map<String, Object> getByHousdeholderId(Long id) {
        Map<String, Object> result = new HashMap<>();
        Householder householder = this.getById(id);
        Community community = communityService.getById(householder.getCommunityId());
        Address address = addressService.getById(householder.getAddressId());
        result.put("householderName", householder.getHouseholderName());
        result.put("householderNum", String.valueOf(householder.getHouseholderNum()));
        result.put("householderCommunity", community.getCommunityName());
        result.put("householderStreet", address.getHouseholderStreet());
        result.put("householderUnit", address.getHouseholderUnit());
        result.put("householderHouseNum", address.getHouseholderHouseNum());
        result.put("householdId", householder.getHouseholdId());
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateHouseholder(Map<String, Object> params) {
        QueryWrapper<Community> wrapper = new QueryWrapper<>();
        Long householderId = Long.parseLong((String) params.get("id"));
        String householderName = (String) params.get("householderName");
        int householderNum = Integer.parseInt((String) params.get("householderNum"));
        String householderCommunity = (String) params.get("householderCommunity");
        String householderStreet = (String) params.get("householderStreet");
        String householderUnit = (String) params.get("householderUnit");
        String householderHouseNum = (String) params.get("householderHouseNum");
        Householder householder = this.getById(householderId);
        Address address = addressService.getById(householder.getCommunityId());
        wrapper.eq("community_name", householderCommunity);
        List<Object> communityList = communityService.listObjs(wrapper);
        Long communityId = (long) communityList.get(0).hashCode();

        householder.setHouseholderName(householderName);
        householder.setHouseholderNum(householderNum);
        householder.setCommunityId(communityId);

        address.setHouseholderStreet(householderStreet);
        address.setHouseholderHouseNum(householderHouseNum);
        address.setHouseholderUnit(householderUnit);

        this.updateById(householder);
        addressService.updateById(address);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removeByHouseholderIds(Long[] ids) {
        Householder householder;
        Long[] addressIds = new Long[10000];
        for (int i = 0; i < ids.length; i++){
            householder = this.getById(ids[i]);
            addressIds[i] = householder.getAddressId();
        }
        this.removeByIds(Arrays.asList(ids));
        addressService.removeByIds(Arrays.asList(addressIds));
    }
    
}