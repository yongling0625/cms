package online.lianxue.cms.system.service;

import online.lianxue.cms.system.entity.SysMenu;
import online.lianxue.cms.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import online.lianxue.cms.system.entity.SysRoleMenu;

import java.util.List;

/**
 * <p>
 * 角色管理 服务类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 查询角色菜单集合
     * @return
     */
    List<SysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    int saveRoleMenus(List<SysRoleMenu> records);

}
