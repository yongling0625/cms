package online.lianxue.cms.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import online.lianxue.cms.common.entity.CreateUpdateEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 机构管理
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends CreateUpdateEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 上级机构ID，一级机构为0
     */
    private Long parentId;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    private Integer delFlag;

    // 非数据库字段
    @TableField(exist = false)
    private List<SysDept> children;
    // 非数据库字段
    @TableField(exist = false)
    private String parentName;
    // 非数据库字段
    @TableField(exist = false)
    private Integer level;


}
