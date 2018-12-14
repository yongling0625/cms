package online.lianxue.cms.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import online.lianxue.cms.system.entity.SysDept;
import online.lianxue.cms.system.mapper.SysDeptMapper;
import online.lianxue.cms.system.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 机构管理 服务实现类
 * </p>
 *
 * @author lianxue
 * @since 2018-11-22
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public int saveO(SysDept record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysDeptMapper.insert(record);
        }
        return sysDeptMapper.updateById(record);
    }

    @Override
    public List<SysDept> findTree() {
        List<SysDept> sysDepts = new ArrayList<>();
        List<SysDept> depts = sysDeptMapper.selectList(new QueryWrapper<>());
        for (SysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                dept.setLevel(0);
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
        return sysDepts;
    }

    private void findChildren(List<SysDept> sysDepts, List<SysDept> depts) {
        for (SysDept sysDept : sysDepts) {
            List<SysDept> children = new ArrayList<>();
            for (SysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(dept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }
}
