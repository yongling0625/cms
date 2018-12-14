package online.lianxue.cms.system.entity;

import online.lianxue.cms.common.entity.CreateUpdateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统日志
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLog extends CreateUpdateEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;


}
