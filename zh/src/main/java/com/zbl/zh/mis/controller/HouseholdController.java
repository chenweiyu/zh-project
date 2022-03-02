package com.zbl.zh.mis.controller;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.common.core.R;
import com.zbl.zh.mis.entity.Household;
import com.zbl.zh.mis.service.HouseholdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/household")
public class HouseholdController {

    @Autowired
    HouseholdService householdService;

    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage<Household> list = householdService.pageAllHousehold(params);
        return R.ok(list);
    }

    @PostMapping("/save")
    public R save(@Validated @RequestBody Household household) {
        householdService.saveHousehold(household);
        return R.ok();
    }
    

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        Household householdInfo = householdService.getByHousdeholdId(id);
        return R.ok(householdInfo);
    }

    @PostMapping("/update")
    public R update(@Validated @RequestBody Household household) {
        householdService.updateHousehold(household);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        householdService.removeByHouseholdIds(ids);
        return R.ok();
    }

    @PostMapping("/sameCommunity")
    public R sameCommunity(@RequestParam Map<String, Object> params) {
        IPage<Household> list = householdService.getByIdSameCommunity(params);
        return R.ok(list);
    }
    
}
