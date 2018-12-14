package online.lianxue.cms.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.common.response.HttpResult;
import online.lianxue.cms.system.entity.SysDict;
import online.lianxue.cms.system.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 字典控制器
 */
@RestController
@RequestMapping("dict")
public class SysDictController {

	@Autowired
	private SysDictService sysDictService;

	@RequiresPermissions({"sys:dict:add", "sys:dict:edit"})
	@PostMapping(value="/save")
	public HttpResult save(@RequestBody SysDict record) {
		return HttpResult.ok(sysDictService.save(record));
	}

	@RequiresPermissions("sys:dict:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<SysDict> records) {
		return HttpResult.ok(sysDictService.removeByIds(records));
	}

	@RequiresPermissions("sys:dict:view")
	@PostMapping(value="/findPage")
	public HttpResult findPage(@RequestBody Page page) {
		return HttpResult.ok(sysDictService.page(page));
	}

	@RequiresPermissions("sys:dict:view")
	@GetMapping(value="/findByLable")
	public HttpResult findByLable(@RequestParam String lable) {
		return HttpResult.ok(sysDictService.getOne(new QueryWrapper<SysDict>().eq("label", lable)));
	}
}
