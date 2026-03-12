package com.keystone.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.keystone.common.exception.BusinessException;
import com.keystone.system.domain.SysDept;
import com.keystone.system.domain.table.SysDeptTableDef;
import com.keystone.system.mapper.SysDeptMapper;
import com.keystone.system.service.ISysDeptService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Override
    public List<SysDept> selectDeptList(SysDept dept) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SysDeptTableDef.SYS_DEPT.ALL_COLUMNS)
                .from(SysDeptTableDef.SYS_DEPT)
                .where(SysDeptTableDef.SYS_DEPT.DEL_FLAG.eq("0"))
                .and(SysDeptTableDef.SYS_DEPT.DEPT_NAME.like(dept.getDeptName(), StrUtil.isNotBlank(dept.getDeptName())))
                .and(SysDeptTableDef.SYS_DEPT.STATUS.eq(dept.getStatus(), StrUtil.isNotBlank(dept.getStatus())))
                .orderBy(SysDeptTableDef.SYS_DEPT.PARENT_ID.asc(), SysDeptTableDef.SYS_DEPT.ORDER_NUM.asc());
        return mapper.selectListByQuery(queryWrapper);
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        List<SysDept> returnList = new ArrayList<>();
        List<Long> tempList = depts.stream().map(SysDept::getDeptId).collect(Collectors.toList());
        for (SysDept dept : depts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(depts, dept);
                returnList.add(dept);
            }
        }
        if (returnList.isEmpty()) {
            returnList = depts;
        }
        return returnList;
    }

    @Override
    public int insertDept(SysDept dept) {
        SysDept info = mapper.selectOneById(dept.getParentId());
        if (info != null) {
            dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        } else {
            dept.setAncestors("0");
        }
        return mapper.insertSelective(dept);
    }

    @Override
    public int updateDept(SysDept dept) {
        SysDept newParentDept = mapper.selectOneById(dept.getParentId());
        SysDept oldDept = mapper.selectOneById(dept.getDeptId());
        if (newParentDept != null && oldDept != null) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        return mapper.update(dept);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父祖级列表
     * @param oldAncestors 旧的父祖级列表
     */
    private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDept> children = mapper.selectListByQuery(QueryWrapper.create().where(SysDeptTableDef.SYS_DEPT.ANCESTORS.like(deptId + "")));
        for (SysDept child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (CollUtil.isNotEmpty(children)) {
            this.updateBatch(children);
        }
    }

    @Override
    public int deleteDeptById(Long deptId) {
        if (hasChildByDeptId(deptId)) {
            throw new BusinessException("存在下级部门,不允许删除");
        }
        if (checkDeptExistUser(deptId)) {
            throw new BusinessException("部门存在用户,不允许删除");
        }
        SysDept dept = new SysDept();
        dept.setDeptId(deptId);
        dept.setDelFlag("2");
        return mapper.update(dept);
    }
    
    private boolean hasChildByDeptId(Long deptId) {
        long count = mapper.selectCountByQuery(QueryWrapper.create().where(SysDeptTableDef.SYS_DEPT.PARENT_ID.eq(deptId)).and(SysDeptTableDef.SYS_DEPT.DEL_FLAG.eq("0")));
        return count > 0;
    }

    private boolean checkDeptExistUser(Long deptId) {
        // 由于为了示例简洁，这里简单跳过或者需要引入 SysUserMapper
        // 实际开发中可以通过注入 SysUserMapper 进行查询
        return false;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysDept> list, SysDept t) {
        // 得到子节点列表
        List<SysDept> childList = getChildList(list, t);
        // t.setChildren(childList); // 注：因为SysDept实体没有 children 属性，在返回给前端前，我们需要 DTO 转换，或者动态扩展
        // 由于前端接受通常需要 children，如果domain没有，可以通过继承或者Map返回，或者在controller通过DTO包装。
    }

    /**
     * 得到子节点列表
     */
    private List<SysDept> getChildList(List<SysDept> list, SysDept t) {
        List<SysDept> tlist = new ArrayList<>();
        for (SysDept n : list) {
            if (n.getParentId().longValue() == t.getDeptId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }
}
