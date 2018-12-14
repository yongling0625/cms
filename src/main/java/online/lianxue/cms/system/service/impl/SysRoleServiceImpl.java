package online.lianxue.cms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.lianxue.cms.system.entity.SysMenu;
import online.lianxue.cms.system.entity.SysRole;
import online.lianxue.cms.system.entity.SysRoleMenu;
import online.lianxue.cms.system.mapper.SysMenuMapper;
import online.lianxue.cms.system.mapper.SysRoleMapper;
import online.lianxue.cms.system.mapper.SysRoleMenuMapper;
import online.lianxue.cms.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色管理 服务实现类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<SysMenu> findRoleMenus(Long roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if("admin".equalsIgnoreCase(sysRole.getName())) {
            // 如果是超级管理员，返回全部
            return sysMenuMapper.selectList(null);
        }
        return sysMenuMapper.findRoleMenus(roleId);
    }

    @Transactional
    @Override
    public int saveRoleMenus(List<SysRoleMenu> records) {
        if(records == null || records.isEmpty()) {
            return 1;
        }
        Long roleId = records.get(0).getRoleId();
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));
        for(SysRoleMenu record:records) {
            sysRoleMenuMapper.insert(record);
        }
        return 1;
    }
}
