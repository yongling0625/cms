package online.lianxue.cms.system.service.impl;

import online.lianxue.cms.shiro.JWTUtil;
import online.lianxue.cms.system.entity.SysUser;
import online.lianxue.cms.system.entity.SysUserToken;
import online.lianxue.cms.system.mapper.SysUserTokenMapper;
import online.lianxue.cms.system.service.SysUserTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户Token 服务实现类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements SysUserTokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SysUserToken createToken(SysUser sysUser) {
        // 生成一个token
        String token = JWTUtil.generateToken(sysUser.getId(),sysUser.getName(),sysUser.getPassword());
        // 判断是否生成过token
        SysUserToken sysUserToken = (SysUserToken) redisTemplate.opsForValue().get("token" + sysUser.getId());
        if(sysUserToken == null){
            sysUserToken = new SysUserToken();
            sysUserToken.setUserId(sysUser.getId());
            sysUserToken.setToken(token);
            sysUserToken.setExpireTime(LocalDateTime.now().plusDays(1));
        } else{
            sysUserToken.setToken(token);
            sysUserToken.setExpireTime(LocalDateTime.now().plusDays(1));
        }
        redisTemplate.opsForValue().set("token" + sysUser.getId(), sysUserToken);
        return sysUserToken;
    }
}
