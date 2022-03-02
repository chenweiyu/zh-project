package com.zbl.zh.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "address")
public class Address {
    @TableId
    private Long id;

    private String householderStreet;
    private String householderUnit;
    private String householderHouseNum;


    public Address() {
    }

    public Address(String householderStreet2, String householderUnit2, String householderHouseNum2) {
        this.householderStreet = householderStreet2;
        this.householderUnit = householderUnit2;
        this.householderHouseNum = householderHouseNum2;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHouseholderStreet() {
        return this.householderStreet;
    }

    public void setHouseholderStreet(String householderStreet) {
        this.householderStreet = householderStreet;
    }

    public String getHouseholderUnit() {
        return this.householderUnit;
    }

    public void setHouseholderUnit(String householderUnit) {
        this.householderUnit = householderUnit;
    }

    public String getHouseholderHouseNum() {
        return this.householderHouseNum;
    }

    public void setHouseholderHouseNum(String householderHouseNum) {
        this.householderHouseNum = householderHouseNum;
    }

}
