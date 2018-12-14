package online.lianxue.cms.system.mapper;

import online.lianxue.cms.system.entity.SysMenu;
import online.lianxue.cms.system.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色菜单 Mapper 接口
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    @Select("SELECT m.* FROM sys_menu m JOIN sys_role_menu rm on rm.menu_id = m.id where rm.role_id = #{roleId} and m.type = 2")
    List<SysMenu> findPermsByRoleId(Long roleId);
}
