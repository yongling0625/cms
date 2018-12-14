package online.lianxue.cms.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import online.lianxue.cms.system.entity.SysMenu;
import online.lianxue.cms.system.entity.SysUser;
import online.lianxue.cms.system.entity.SysUserRole;
import online.lianxue.cms.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * shiro权限认证
 */
@Slf4j
@Component
public class MyShiroRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private SysRoleService sysRoleService;
    @Lazy
    @Autowired
    private SysMenuService sysMenuService;
    @Lazy
    @Autowired
    private RedisTemplate redisTemplate;
    @Lazy
    @Autowired
    SysUserService sysUserService;
    @Lazy
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    @Lazy
    SysUserTokenService sysUserTokenService;


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) {
        log.info("Shiro开始登陆认证");
        String token = (String) authcToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }
        SysUser user = (SysUser) redisTemplate.opsForValue().get(username);
        if (user == null) {
            user = sysUserService.getOne(new QueryWrapper<SysUser>().eq("name", username));
        }
        if (!JWTUtil.verify(token, user.getId(), username, user.getPassword())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        // 账号被锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        redisTemplate.opsForValue().set(username, user);
        //缓存信息
        return new SimpleAuthenticationInfo(user.getId(), token, getName());

    }

    /**
     * Shiro权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        log.info("Shiro开始权限认证");
        Integer userId = Integer.valueOf(principals.getPrimaryPrincipal().toString());
        Set<String> permissionSet = new HashSet<String>();
        if(userId == 1){  //超级管理员
            List<SysMenu> menuList = sysMenuService.list();
            menuList.forEach(sysMenu -> {
                if (StringUtils.isNotBlank(sysMenu.getPerms())) {
                    permissionSet.add(sysMenu.getPerms());
                }
            });
        }else{
            List<SysUserRole> userRoleList = sysUserRoleService.list(new QueryWrapper<SysUserRole>().select("role_id").eq("user_id", userId));
            for (SysUserRole userRole : userRoleList) {
                List<SysMenu> roleMenus = sysRoleService.findRoleMenus(userRole.getRoleId());
                roleMenus.parallelStream().forEach(roleMenu -> {
                    if (StringUtils.isNoneBlank(roleMenu.getPerms())) {
                        permissionSet.add(roleMenu.getPerms());
                    }
                });
            }
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissionSet);
        return info;
    }


}
