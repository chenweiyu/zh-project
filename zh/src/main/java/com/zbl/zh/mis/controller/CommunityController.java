package com.zbl.zh.mis.controller;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.common.core.R;
import com.zbl.zh.mis.entity.Community;
import com.zbl.zh.mis.service.CommunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        if (params.size() == 0) {
            List<Community> resultList = communityService.list();
            return R.ok(resultList);
        }
        IPage<Community> list = communityService.pageAllCommunity(params);
        return R.ok(list);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        log.info("hhhh ---- " + id);
        Community communityInfo = communityService.getByCommunityId(id);
        return R.ok(communityInfo);
    }

    @PostMapping("/save")
    public R save(@Validated @RequestBody Community community) {
        log.info("社区---" + community);
        communityService.saveCommunity(community);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@Validated @RequestBody Community community) {
        communityService.updateCommunity(community);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        communityService.removeByCommunityIds(ids);
        return R.ok();
    }
    
}
