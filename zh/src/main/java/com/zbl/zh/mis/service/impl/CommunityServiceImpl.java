package com.zbl.zh.mis.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.zh.common.core.P;
import com.zbl.zh.mis.dao.CommunityDao;
import com.zbl.zh.mis.entity.Community;
import com.zbl.zh.mis.entity.CommunityExpand;
import com.zbl.zh.mis.service.CommunityExpandService;
import com.zbl.zh.mis.service.CommunityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityDao, Community> implements CommunityService{

    @Autowired
    CommunityExpandService communityExpandService;

    @Override
    public IPage<Community> pageAllCommunity(Map<String, Object> params) {
        IPage<Community> result;
        QueryWrapper<Community> Q = new QueryWrapper<Community>();

        String query = (String) params.get("query");
        if (query != null && !query.isEmpty()) {
            Q.like("community_name", query);
        }
        result = this.page(P.page(params), Q);
        return result;
    }

    @Override
    public Community getByCommunityId(Long id) {
        Community result = this.getById(id);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveCommunity(Community community) {
        QueryWrapper<Community> wrapper = new QueryWrapper<>();
        String communityName = community.getCommunityName();
        this.save(community);
        wrapper.eq("community_name", communityName);
        List<Object> communityList = this.listObjs(wrapper);
        Long communityId = (long) communityList.get(0).hashCode();
        Long communityExpId = communityId;
        CommunityExpand communityExpand = new CommunityExpand(communityExpId, communityId);
        communityExpandService.save(communityExpand);
    }

    @Override
    public void updateCommunity(Community community) {
        this.updateById(community);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void removeByCommunityIds(Long[] ids) {
        this.removeByIds(Arrays.asList(ids));
        communityExpandService.removeByIds(Arrays.asList(ids));
    }
    
}