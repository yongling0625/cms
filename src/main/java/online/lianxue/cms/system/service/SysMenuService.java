package online.lianxue.cms.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.lianxue.cms.system.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 查询菜单树,用户ID和用户名为空则查询全部
     * @param menuType 获取菜单类型，0：获取所有菜单，包含按钮，1：获取所有菜单，不包含按钮
     * @return
     */
    List<SysMenu> findTree(Long userId, int menuType);

    /**
     * 根据用户id 查找菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> findByUserId(Long userId);

}
