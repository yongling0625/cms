package online.lianxue.cms.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.common.response.HttpResult;
import online.lianxue.cms.system.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制器
 */
@RestController
@RequestMapping("log")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;

	@RequiresPermissions("sys:log:view")
	@PostMapping(value="/findPage")
	public HttpResult findPage(@RequestBody Page page) {
		return HttpResult.ok(sysLogService.page(page));
	}
}
