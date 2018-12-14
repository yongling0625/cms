package online.lianxue.cms.system.service;

import online.lianxue.cms.system.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 机构管理 服务类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询机构树
     * @return
     */
    List<SysDept> findTree();
}
