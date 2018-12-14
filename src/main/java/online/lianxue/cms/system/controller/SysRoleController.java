package online.lianxue.cms.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.common.response.HttpResult;
import online.lianxue.cms.system.entity.SysRole;
import online.lianxue.cms.system.entity.SysRoleMenu;
import online.lianxue.cms.system.mapper.SysRoleMapper;
import online.lianxue.cms.system.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("role")
public class SysRoleController {

	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@RequiresPermissions({"sys:role:add", "sys:role:edit"})
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody SysRole record) {
		SysRole role = sysRoleService.getById(record.getId());
		if(role != null) {
			if("admin".equalsIgnoreCase(role.getName())) {
				return HttpResult.error("超级管理员不允许修改!");
			}
		}
		// 新增角色
		if((record.getId() == null || record.getId() ==0) && sysRoleService.getOne(new QueryWrapper<SysRole>().eq("name",record.getName())) != null) {
			return HttpResult.error("角色名已存在!");
		}
		return HttpResult.ok(sysRoleService.save(record));
	}

	@RequiresPermissions("sys:role:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<SysRole> records) {
		return HttpResult.ok(sysRoleService.removeByIds(records));
	}

	@RequiresPermissions("sys:role:view")
	@PostMapping(value="/findPage")
	public HttpResult findPage(@RequestBody Page page) {
		return HttpResult.ok(sysRoleService.page(page));
	}
	
	@RequiresPermissions("sys:role:view")
	@GetMapping(value="/findAll")
	public HttpResult findAll() {
		return HttpResult.ok(sysRoleService.list());
	}
	
	@RequiresPermissions("sys:role:view")
	@GetMapping(value="/findRoleMenus")
	public HttpResult findRoleMenus(@RequestParam Long roleId) {
		return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
	}
	
	@RequiresPermissions("sys:role:view")
	@PostMapping(value="/saveRoleMenus")
	public HttpResult saveRoleMenus(@RequestBody List<SysRoleMenu> records) {
		for(SysRoleMenu record:records) {
			SysRole sysRole = sysRoleMapper.selectById(record.getRoleId());
			if("admin".equalsIgnoreCase(sysRole.getName())) {
				// 如果是超级管理员，不允许修改
				return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
			}
		}
		return HttpResult.ok(sysRoleService.saveRoleMenus(records));
	}
}
