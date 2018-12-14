package online.lianxue.cms.system.entity;

import online.lianxue.cms.common.entity.CreateUpdateEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户Token
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserToken extends CreateUpdateEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    /**
     * token
     */
    private String token;

    /**
     * 过期时间
     */
    private LocalDateTime expireTime;


}
