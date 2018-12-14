package online.lianxue.cms.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.lianxue.cms.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select m.* from sys_menu m, sys_role_menu rm where rm.role_id = #{roleId,jdbcType=BIGINT} and m.id = rm.menu_id")
    List<SysMenu> findRoleMenus(@Param(value="roleId") Long roleId);


    @Select("SELECT DISTINCT(m.id),m.* FROM sys_menu m " +
            "LEFT JOIN sys_role_menu rm ON rm.menu_id = m.id " +
            "LEFT JOIN sys_user_role ur ON ur.role_id = rm.role_id " +
            "LEFT JOIN sys_user u ON u.id = ur.user_id " +
            "WHERE u.id = #{userId}")
    List<SysMenu> findByUserId(Long userId);
}
