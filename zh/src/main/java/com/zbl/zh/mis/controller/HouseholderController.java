package com.zbl.zh.mis.controller;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.common.core.R;
import com.zbl.zh.mis.entity.Householder;
import com.zbl.zh.mis.service.HouseholderService;

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
@RequestMapping("/householder")
public class HouseholderController {

    @Autowired
    HouseholderService householderService;

    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        if (params.size() == 0) {
            List<Householder> resultList = householderService.list();
            return R.ok(resultList);
        }
        IPage<Map<String, Object>> list = householderService.pageAllHouseholder(params);
        return R.ok(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/save")
    public R save(@RequestParam Map<String, Object> params) {
        householderService.saveHouseholder(params);
        return R.ok();
    }
    

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        Map<String, Object> householderInfo = householderService.getByHousdeholderId(id);
        return R.ok(householderInfo);
    }

    @PostMapping("/update")
    public R update(@RequestParam Map<String, Object> params) {
        householderService.updateHouseholder(params);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        householderService.removeByHouseholderIds(ids);
        return R.ok();
    }
}
