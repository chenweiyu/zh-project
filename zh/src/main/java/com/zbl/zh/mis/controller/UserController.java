package com.zbl.zh.mis.controller;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zbl.zh.common.core.P;
import com.zbl.zh.common.core.R;
import com.zbl.zh.common.core.U;
import com.zbl.zh.mis.entity.Role;
import com.zbl.zh.mis.entity.User;
import com.zbl.zh.mis.service.UserRoleService;
import com.zbl.zh.mis.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        // 构造查询
        QueryWrapper<User> Q = new QueryWrapper<User>();
        String name = (String) params.get("name");
        String rid = (String) params.get("rid");
        if (name != null && !name.isEmpty())
            Q.like("name", name);

        // select * from sys_user where name like '%6%' and id in (select user_id from
        // sys_user_role where role_id=9 )
        // SELECT id,name,password,birthday,email,avatar,create_time FROM sys_user WHERE
        // (name LIKE ? AND id IN (select user_id from sys_user_role where role_id=9))
        // ORDER BY create_time DESC LIMIT ?
        if (rid != null && !rid.isEmpty())
            Q.inSql("id", "select user_id from user_role where role_id=" + rid); // 子查询
        Q.orderByDesc("create_time");

        IPage<User> pageData = userService.page(P.page(params), Q);

        // 替换角色名称
        for (User u : pageData.getRecords()) {
            List<Role> list = userRoleService.getRoleByUserId(u.getId());
            // u.setMyRoleName(list.stream().map(Role::getRoleName).toArray(String[]::new));
            // //
            u.setMyRoleList(list);
        }

        return R.ok(pageData);
    }

    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        User entity = userService.getById(id);
        entity.setPassword(null); // 不送前台
        List<Role> list = userRoleService.getRoleByUserId(entity.getId());

        entity.setYesCheckList(list.stream().map(Role::getId).toArray(Long[]::new)); // 把角色ID数组拿过来
        return R.ok(entity);
    }

    /**
     * 保存用户
     * 
     */
    @PostMapping("/save")
    public R save(MultipartFile file, String user) {

        // 2.保存用户
        User entity = JSONObject.parseObject(user, User.class);
        // 进入库前做加密
        entity.setPassword(DigestUtils.md5DigestAsHex(entity.getPassword().getBytes()));

        userService.saveUserAndRole(entity);

        return R.ok();
    }

    @PostMapping("/update")
    public R update(MultipartFile file, String user) {
        User entity = JSONObject.parseObject(user, User.class);
        // 更新密码加密
        if (entity.getPassword() != null && entity.getPassword().length() > 0) {
            entity.setPassword(DigestUtils.md5DigestAsHex(entity.getPassword().getBytes()));
        }
        userService.updateUserAndRole(entity);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@U User user, @RequestBody Long[] ids) {
        // 1.先删图片
        for (Long id : ids) {

            if (id == 1L)
                return R.error("超级管理员不能删除");
            if (user.getId() == id)
                return R.error("当前用户不能删除");
        }
        userService.deleteUserAndRole(ids);

        return R.ok();
    }

    /**
     * 用户登录 API
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public R login(@Validated @RequestBody User entity) throws Exception {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", entity.getUsername()).eq("password",
                entity.getPassword()));
        if (user == null)
            return R.error("账号或密码错误！");
        return R.ok(user);
    }

    /**
     * 获取当前用户 在需要注入当前用户的方法里写入参数 @U User user
     * 
     * @return
     */
    @PostMapping("/getCurrentUser/{id}")
    public R getCurrentUser(@PathVariable("id") Long id) {
        log.info("arg0---" + id);
        User user = userService.getById(id);
        log.info("arg1---" + user.getUsername());
        return R.ok(user);
    }
    
}
