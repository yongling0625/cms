package online.lianxue.cms.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.system.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import online.lianxue.cms.system.entity.SysUserRole;
import online.lianxue.cms.system.vo.SysUserVo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-21
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户id 查找权限标识集合
     * @param userId
     * @return
     */
    Set<String> findPermissions(Long userId);

    /**
     * 查找用户的角色集合
     * @param userId
     * @return
     */
    List<SysUserRole> findUserRoles(Long userId);


    Page<SysUserVo> getUser(Page<SysUserVo> page, SysUser user);

    Boolean removeByUserIds(Collection<? extends Serializable> ids);
}
