package com.zbl.zh.mis.service;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbl.zh.mis.entity.Community;

public interface CommunityService extends IService<Community> {

    public IPage<Community> pageAllCommunity(Map<String, Object> params);

    public Community getByCommunityId(Long id);

    public void saveCommunity(Community community);

    public void updateCommunity(Community community);

    public void removeByCommunityIds(Long[] ids);

}