package online.lianxue.cms.system.controller;

import online.lianxue.cms.common.response.HttpResult;
import online.lianxue.cms.system.entity.SysMenu;
import online.lianxue.cms.system.service.SysMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 菜单控制器
 */
@RestController
@RequestMapping("menu")
public class SysMenuController {

	@Autowired
	private SysMenuService sysMenuService;

	@RequiresPermissions({"sys:menu:add", "sys:menu:edit"})
	@PostMapping(value="/save")
	public HttpResult saveOrUpdate(@RequestBody SysMenu record) {
		return HttpResult.ok(sysMenuService.saveOrUpdate(record));
	}

	@RequiresPermissions("sys:menu:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<SysMenu> records) {
		List<Long> idList = new ArrayList<>();
		records.forEach(record -> {
			idList.add(record.getId());
		});
		return HttpResult.ok(sysMenuService.removeByIds(idList));
	}

	@RequiresPermissions("sys:menu:view")
	@GetMapping(value="/findNavTree")
	public HttpResult findNavTree(@RequestParam Long userId) {
		return HttpResult.ok(sysMenuService.findTree(userId, 1));
	}

	@RequiresPermissions("sys:menu:view")
	@GetMapping(value="/findMenuTree")
	public HttpResult findMenuTree() {
		return HttpResult.ok(sysMenuService.findTree(null, 0));
	}
}
