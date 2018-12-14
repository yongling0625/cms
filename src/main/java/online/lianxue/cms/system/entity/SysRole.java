package online.lianxue.cms.system.entity;

import online.lianxue.cms.common.entity.CreateUpdateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色管理
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends CreateUpdateEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Integer delFlag;


}
