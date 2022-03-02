package com.zbl.zh.mis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbl.zh.mis.dao.AddressDao;
import com.zbl.zh.mis.entity.Address;
import com.zbl.zh.mis.service.AddressService;

import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService{
    
}