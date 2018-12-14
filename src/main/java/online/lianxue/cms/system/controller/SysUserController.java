package online.lianxue.cms.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.common.controller.BaseController;
import online.lianxue.cms.common.response.HttpResult;
import online.lianxue.cms.common.util.PasswordUtils;
import online.lianxue.cms.system.entity.SysUser;
import online.lianxue.cms.system.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequiresPermissions({"sys:user:add", "sys:user:edit"})
    @PostMapping(value = "/save")
    public HttpResult saveOrUpdate(@RequestBody SysUser record) {
        SysUser user = sysUserService.getById(record.getId());
        if (user != null) {
            if ("admin".equalsIgnoreCase(user.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        if (record.getPassword() != null) {
            String salt = PasswordUtils.getSalt();
            if (user == null) {
                // 新增用户
                if (sysUserService.getOne(new QueryWrapper<SysUser>().eq("name", record.getName())) != null) {
                    return HttpResult.error("用户名已存在!");
                }
                String password = PasswordUtils.encrypte(record.getPassword(), salt);
                record.setSalt(salt);
                record.setPassword(password);
                sysUserService.save(record);
            } else {
                String password = PasswordUtils.encrypte(record.getPassword(), salt);
                record.setSalt(salt);
                record.setPassword(password);
                sysUserService.updateById(record);
            }
        }
        return HttpResult.ok();
    }

    @RequiresPermissions("sys:user:delete")
    @PostMapping(value = "/delete")
    public HttpResult delete(@RequestBody List<SysUser> records) {
        List<Long> idList = new ArrayList<>();
        for (SysUser record : records) {
            if (1 == record.getId()) {
                return HttpResult.error("超级管理员不允许删除!");
            }
            SysUser sysUser = sysUserService.getById(record.getId());
            idList.add(sysUser.getId());
        }
        return HttpResult.ok(sysUserService.removeByUserIds(idList));
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(value = "/findByName")
    public HttpResult findByUserName(@RequestParam String name) {
        return HttpResult.ok(sysUserService.getOne(new QueryWrapper<SysUser>().eq("name", name)));
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(value = "/findPermissions")
    public HttpResult findPermissions(@RequestParam Long userId) {
        return HttpResult.ok(sysUserService.findPermissions(userId));
    }

    @RequiresPermissions("sys:user:view")
    @GetMapping(value = "/findUserRoles")
    public HttpResult findUserRoles(@RequestParam Long userId) {
        return HttpResult.ok(sysUserService.findUserRoles(userId));
    }

    @RequiresPermissions("sys:user:view")
    @PostMapping(value = "/findPage")
    public HttpResult findPage(Page page, @RequestBody SysUser user) {
        return HttpResult.ok(sysUserService.getUser(page, user));
    }

//    /**
//     * 修改登录用户密码
//     */
//    @RequiresPermissions("sys:user:edit")
//    @GetMapping("/updatePassword")
//    public HttpResult updatePassword(@RequestParam String password, @RequestParam String newPassword) {
//        SysUser user = ShiroUtils.getUser();
//        if (user != null && password != null && newPassword != null) {
//            String oldPassword = PasswordUtils.encrypte(password, user.getSalt());
//            if (!oldPassword.equals(user.getPassword())) {
//                return HttpResult.error("原密码不正确");
//            }
//            user.setPassword(PasswordUtils.encrypte(newPassword, user.getSalt()));
//            HttpResult.ok(sysUserService.save(user));
//        }
//        return HttpResult.error();
//    }

}
