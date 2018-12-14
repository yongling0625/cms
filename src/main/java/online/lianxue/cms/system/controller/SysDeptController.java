package online.lianxue.cms.system.controller;

import online.lianxue.cms.common.response.HttpResult;
import online.lianxue.cms.system.entity.SysDept;
import online.lianxue.cms.system.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("dept")
public class SysDeptController {

	@Autowired
	private SysDeptService sysDeptService;
	
	@RequiresPermissions({"sys:dept:add", "sys:dept:edit"})
	@PostMapping(value="/save")
	public HttpResult saveOrUpdate(@RequestBody SysDept record) {
		return HttpResult.ok(sysDeptService.saveOrUpdate(record));
	}

	@RequiresPermissions("sys:dept:delete")
	@PostMapping(value="/delete")
	public HttpResult delete(@RequestBody List<SysDept> records) {
		List<Long> idList = new ArrayList<>();
		records.forEach(record -> {
			idList.add(record.getId());
		});
		return HttpResult.ok(sysDeptService.removeByIds(idList));
	}

	@RequiresPermissions("sys:dept:view")
	@GetMapping(value="/findTree")
	public HttpResult findTree() {
		return HttpResult.ok(sysDeptService.findTree());
	}

}
