package online.lianxue.cms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.system.entity.*;
import online.lianxue.cms.system.mapper.*;
import online.lianxue.cms.system.service.SysMenuService;
import online.lianxue.cms.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.lianxue.cms.system.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 用户管理 服务实现类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

//    /**
//     * 加载用户角色
//     * @param pageResult
//     */
//    private void findUserRoles(PageResult pageResult) {
//        List<?> content = pageResult.getContent();
//        for(Object object:content) {
//            SysUser sysUser = (SysUser) object;
//            List<SysUserRole> userRoles = findUserRoles(sysUser.getId());
//            sysUser.setUserRoles(userRoles);
//            sysUser.setRoleNames(getRoleNames(userRoles));
//        }
//    }

    private String getRoleNames(List<SysUserRole> userRoles) {
        StringBuilder sb = new StringBuilder();
        for (Iterator<SysUserRole> iter = userRoles.iterator(); iter.hasNext(); ) {
            SysUserRole userRole = iter.next();
            SysRole sysRole = sysRoleMapper.selectById(userRole.getRoleId());
            if (sysRole == null) {
                continue;
            }
            sb.append(sysRole.getRemark());
            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    @Override
    public Set<String> findPermissions(Long userId) {
        Set<String> perms = new HashSet<>();
        if(userId == 1){  //超级管理员
            List<SysMenu> menuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("type", 2));
            menuList.forEach(sysMenu -> {
                if (StringUtils.isNotBlank(sysMenu.getPerms())) {
                    perms.add(sysMenu.getPerms());
                }
            });
            return perms;
        }
        List<SysUserRole> userRoles = findUserRoles(userId);
        userRoles.forEach(userRole -> {
            List<SysMenu> menuList = sysRoleMenuMapper.findPermsByRoleId(userRole.getRoleId());
            menuList.forEach(sysMenu -> {
                if (StringUtils.isNotBlank(sysMenu.getPerms())) {
                    perms.add(sysMenu.getPerms());
                }
            });
        });
        return perms;
    }

    @Override
    public List<SysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", userId));
    }

    @Override
    public Page<SysUserVo> getUser(Page<SysUserVo> page, SysUser user) {
        return page.setRecords(this.baseMapper.getUserPage(page, user));
    }

    @Override
    @Transactional
    public Boolean removeByUserIds(Collection<? extends Serializable> ids) {
        this.baseMapper.deleteBatchIds(ids);
        ids.forEach(userId -> {
            this.sysUserRoleMapper.delete(new UpdateWrapper<SysUserRole>().eq("user_id", userId));
        });
        return true;
    }
}
