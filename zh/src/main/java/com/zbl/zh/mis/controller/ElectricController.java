package com.zbl.zh.mis.controller;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.common.core.R;
import com.zbl.zh.mis.entity.Electric;
import com.zbl.zh.mis.service.ElectricService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@RestController
@RequestMapping("/electric")
public class ElectricController {

    @Autowired
    ElectricService electricService;

    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage<Electric> list;
        list = electricService.pageElectricMonth(params);
        return R.ok(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/save")
    public R save(@RequestParam Map<String, Object> params) {
        electricService.saveElectric(params);
        return R.ok();
    }
    

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        Map<String, Object> electricInfo = electricService.getByElectricId(id);
        return R.ok(electricInfo);
    }

    @PostMapping("/update")
    public R update(@RequestParam Map<String, Object> params) {
        electricService.updateElectric(params);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        electricService.removeByElectricIds(ids);
        return R.ok();
    }
    
}
