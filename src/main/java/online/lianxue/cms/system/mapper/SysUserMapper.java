package online.lianxue.cms.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.lianxue.cms.system.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.lianxue.cms.system.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户管理 Mapper 接口
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * @param page
     * @return
     */
    @Select("<script>SELECT u.id,u.name,u.password,d.name dept_name,u.email,u.mobile,u.status,u.del_flag,u.create_by,u.create_time,u.last_update_by,u.last_update_time " +
            "FROM sys_user u LEFT JOIN sys_dept d ON u.dept_id = d.id where 1=1 <if test='user.name != null'> and u.name = #{user.name} </if></script>")
    List<SysUserVo> getUserPage(Page<SysUserVo> page,@Param("user") SysUser user);
}
