package online.lianxue.cms.system.vo;

import lombok.Data;
import online.lianxue.cms.common.entity.CreateUpdateEntity;

@Data
public class SysUserVo extends CreateUpdateEntity {

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 机构名称
     */
    private String deptName;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Integer delFlag;

}
