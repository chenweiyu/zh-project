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
import com.zbl.zh.mis.dao.ElectricDao;
import com.zbl.zh.mis.entity.Electric;
import com.zbl.zh.mis.entity.Householder;
import com.zbl.zh.mis.service.ElectricService;
import com.zbl.zh.mis.service.HouseholderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Service
public class ElectricServiceImpl extends ServiceImpl<ElectricDao, Electric> implements ElectricService{

    @Autowired
    HouseholderService householderService;

    @Override
    public IPage<Electric> pageElectricMonth(Map<String, Object> params) {
        String yearMonth = (String) params.get("month");
        if (yearMonth == "" || yearMonth == null){
            Calendar cale = Calendar.getInstance();
            int year = cale.get(Calendar.YEAR);
            int month = cale.get(Calendar.MONTH) + 1;
            String defaultMonth = year + "-" + month + "-1";
            params.put("month", defaultMonth);
        } else {
            String yearMonthList[];
            int month0;
            yearMonthList = yearMonth.split("-");
            int year = Integer.parseInt(yearMonthList[0]);
            month0 = Integer.parseInt(yearMonthList[1]);
            if (month0 == 12) {
                month0 = 0;
                year += 1;
            }
            int month = month0 + 1;
            String defaultMonth = year + "-" + month + "-1";
            params.put("month", defaultMonth);
        }

        IPage<Electric> result= this.baseMapper.pageElectricMonth(P.page(params), params);    
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveElectric(Map<String, Object> params) {
        Calendar cal = Calendar.getInstance();
        QueryWrapper<Householder> wrapper = new QueryWrapper<>();
        double electricPrice = Double.parseDouble((String) params.get("electricPrice"));
        double electricNum = Double.parseDouble((String) params.get("electricNum"));
        double electricMoney = electricNum * electricPrice;
        String electricDateString = (String) params.get("electricDate");
        Date electricDateDeal = Date.valueOf(electricDateString.substring(0, 10));
        cal.setTime(electricDateDeal);
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH) + 2) % 12;
        if (month == 0){
            month = 12;
        }
        String electricStr = year + "-" + month + "-" + "01";
        Date electricDate = Date.valueOf(electricStr);
        String householderName = (String) params.get("householderName");

        wrapper.eq("householder_name", householderName);
        List<Object> householderList = householderService.listObjs(wrapper);
        Long householderId = (long) householderList.get(0).hashCode();

        Electric electric = new Electric(electricPrice, electricMoney, electricNum ,electricDate, householderId);
        this.save(electric);
    }

    @Override
    public Map<String, Object> getByElectricId(Long id) {
        Electric electric =  this.getById(id);
        Householder householder = householderService.getById(electric.getHouseholderId());
        Map<String, Object> result = new HashMap<>();
        // String electricDate = electric.getElectricDate().toString();
        result.put("householderName", householder.getHouseholderName());
        result.put("electricPrice", electric.getElectricPrice());
        result.put("electricNum", electric.getElectricNum());
        result.put("electricMoney", electric.getElectricMoney());
        result.put("electricDate", electric.getElectricDate());
        return result;
    }

    @Override
    public void updateElectric(Map<String, Object> params) {
        Calendar cal = Calendar.getInstance();
        Long id = Long.parseLong((String) params.get("id"));
        double electricPrice = Double.parseDouble((String) params.get("electricPrice"));
        double electricNum = Double.parseDouble((String) params.get("electricNum"));
        double electricMoney = electricNum * electricPrice;
        String electricDateString = (String) params.get("electricDate");
        Date electricDateDeal = Date.valueOf(electricDateString.substring(0, 10));
        cal.setTime(electricDateDeal);
        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH) + 2) % 12;
        if (month == 0){
            month = 12;
        }
        String electricStr = year + "-" + month + "-" + "01";
        Date electricDate = Date.valueOf(electricStr);

        Electric electric = this.getById(id);
        electric.setElectricDate(electricDate);
        electric.setElectricMoney(electricMoney);
        electric.setElectricNum(electricNum);
        electric.setElectricPrice(electricPrice);
        updateById(electric);
    }

    @Override
    public void removeByElectricIds(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }
    
}