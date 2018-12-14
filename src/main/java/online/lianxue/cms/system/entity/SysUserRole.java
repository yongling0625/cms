package online.lianxue.cms.system.entity;

import online.lianxue.cms.common.entity.CreateUpdateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends CreateUpdateEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


}
