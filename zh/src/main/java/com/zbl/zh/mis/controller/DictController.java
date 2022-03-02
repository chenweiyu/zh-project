package com.zbl.zh.mis.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.common.core.P;
import com.zbl.zh.common.core.R;
import com.zbl.zh.mis.entity.Dict;
import com.zbl.zh.mis.service.DictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * controller调用service方法
 * 
 * 尽量保持controller层代码优雅
 * 
 * 
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    // 注入  
    @Autowired
    DictService dictServcie;




    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        QueryWrapper<Dict> Q = new QueryWrapper<Dict>();

        String name = (String) params.get("query");

        if (name != null && !name.isEmpty())
            Q.like("name", name);

        Q.orderByAsc("type").orderByAsc("order_num");   //两个字段排序

        IPage<Dict> pageData = dictServcie.page(P.page(params),Q);
        return R.ok(pageData);
    }



    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        Dict entity = dictServcie.getById(id);
        return R.ok(entity);
    }


    @PostMapping("/save")
    public R save(@Validated @RequestBody Dict entity) {
        dictServcie.save(entity);
        return R.ok();
    }


    @PostMapping("/update")
    public R update(@Validated @RequestBody Dict entity) {
        dictServcie.updateById(entity);
        return R.ok();
    }



    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        dictServcie.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 根据type获取字典，其他页面调用
     * @param id
     * @return
     */
    @GetMapping("/showdict/{type}")
    public R showdict(@PathVariable("type") String type) {
        List<Dict> list = dictServcie.list(new QueryWrapper<Dict>().eq("type", type).orderByAsc("order_num"));
        return R.ok(list);
    }

    




  
}
