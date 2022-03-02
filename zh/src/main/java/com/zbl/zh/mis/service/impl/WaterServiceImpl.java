package com.zbl.zh.mis.service.impl;

import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.zh.common.core.P;
import com.zbl.zh.mis.dao.WaterDao;
import com.zbl.zh.mis.entity.Householder;
import com.zbl.zh.mis.entity.Water;
import com.zbl.zh.mis.service.HouseholderService;
import com.zbl.zh.mis.service.WaterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Service
public class WaterServiceImpl extends ServiceImpl<WaterDao, Water> implements WaterService{

    @Autowired
    HouseholderService householderService;

    @Override
    public IPage<Water> pageWaterMonth(Map<String, Object> params) {
        String yearMonth = (String) params.get("month");
        if (yearMonth == ""){
            Calendar cale = Calendar.getInstance();
            int year = cale.get(Calendar.YEAR);
            int month = cale.get(Calendar.MONTH);
            String defaultMonth = year + "-" + month + "-1";
            params.put("month", defaultMonth);
        }
        else {
            int month0;
            String yearMonthList[];
            yearMonthList = yearMonth.split("-");
            int year = Integer.parseInt(yearMonthList[0]);
            month0 = Integer.parseInt(yearMonthList[1]);
            if (month0 == 12) {
                month0 = 0;
                year += 1;
            }
            int month = Integer.parseInt(yearMonthList[1]) + 1;
            String defaultMonth = year + "-" + month + "-1";
            params.put("month", defaultMonth);
        }
        IPage<Water> result= this.baseMapper.pageWaterMonth(P.page(params), params);    
        return result;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveWater(Map<String, Object> params) {
        Calendar cal = Calendar.getInstance();
        QueryWrapper<Householder> wrapper = new QueryWrapper<>();
        double waterPrice = Double.parseDouble((String) params.get("waterPrice"));
        double waterNum = Double.parseDouble((String) params.get("waterNum"));
        double waterMoney = waterNum * waterPrice;
        String waterDateString = (String) params.get("waterDate");
        Date waterDateDeal = Date.valueOf(waterDateString.substring(0, 10));
        cal.setTime(waterDateDeal);
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH) + 2) % 12;
        if (month == 0){
            month = 12;
        }
        String waterStr = year + "-" + month + "-" + "01";
        Date waterDate = Date.valueOf(waterStr);
        String householderName = (String) params.get("householderName");

        wrapper.eq("householder_name", householderName);
        List<Object> householderList = householderService.listObjs(wrapper);
        Long householderId = (long) householderList.get(0).hashCode();

        Water water = new Water(waterPrice, waterMoney, waterNum ,waterDate, householderId);
        this.save(water);
    }

    @Override
    public Map<String, Object> getByWaterId(Long id) {
        Water water =  this.getById(id);
        Householder householder = householderService.getById(water.getHouseholderId());
        Map<String, Object> result = new HashMap<>();
        // String waterDate = water.getwaterDate().toString();
        result.put("householderName", householder.getHouseholderName());
        result.put("waterPrice", water.getWaterPrice());
        result.put("waterNum", water.getWaterNum());
        result.put("waterMoney", water.getWaterMoney());
        result.put("waterDate", water.getWaterDate());
        return result;
    }

    @Override
    public void updateWater(Map<String, Object> params) {
        Calendar cal = Calendar.getInstance();
        Long id = Long.parseLong((String) params.get("id"));
        double waterPrice = Double.parseDouble((String) params.get("waterPrice"));
        double waterNum = Double.parseDouble((String) params.get("waterNum"));
        double waterMoney = waterNum * waterPrice;
        String waterDateString = (String) params.get("waterDate");
        Date waterDateDeal = Date.valueOf(waterDateString.substring(0, 10));
        cal.setTime(waterDateDeal);
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH) + 2) % 12;
        if (month == 0){
            month = 12;
        }
        String waterStr = year + "-" + month + "-" + "01";
        Date waterDate = Date.valueOf(waterStr);
        Water water = this.getById(id);
        water.setWaterDate(waterDate);
        water.setWaterMoney(waterMoney);
        water.setWaterNum(waterNum);
        water.setWaterPrice(waterPrice);
        updateById(water);
    }

    @Override
    public void removeByWaterIds(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }
}