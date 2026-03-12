package com.keystone.web.controller.system;

import com.keystone.common.core.domain.Result;
import com.keystone.system.domain.SysDept;
import com.keystone.system.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息
 */
@RestController
@RequestMapping("/api/system/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @GetMapping("/list")
    public Result<List<SysDept>> list(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        return Result.ok(depts);
    }

    /**
     * 获取部门树列表
     */
    @GetMapping("/tree")
    public Result<List<Map<String, Object>>> tree(SysDept dept) {
        List<SysDept> depts = deptService.selectDeptList(dept);
        List<Map<String, Object>> trees = buildDeptTree(depts, 0L);
        return Result.ok(trees);
    }

    /**
     * 根据部门编号获取详细信息
     */
    @GetMapping("/{deptId}")
    public Result<SysDept> getInfo(@PathVariable Long deptId) {
        return Result.ok(deptService.getById(deptId));
    }

    /**
     * 新增部门
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysDept dept) {
        deptService.insertDept(dept);
        return Result.ok();
    }

    /**
     * 修改部门
     */
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysDept dept) {
        deptService.updateDept(dept);
        return Result.ok();
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{deptId}")
    public Result<Void> remove(@PathVariable Long deptId) {
        deptService.deleteDeptById(deptId);
        return Result.ok();
    }

    /**
     * 构建树结构
     */
    private List<Map<String, Object>> buildDeptTree(List<SysDept> deptList, Long parentId) {
        List<Map<String, Object>> treeList = new ArrayList<>();
        for (SysDept dept : deptList) {
            if (dept.getParentId().equals(parentId)) {
                Map<String, Object> node = new HashMap<>();
                node.put("id", dept.getDeptId());
                node.put("deptId", dept.getDeptId());
                node.put("parentId", dept.getParentId());
                node.put("name", dept.getDeptName());
                node.put("deptName", dept.getDeptName());
                node.put("orderNum", dept.getOrderNum());
                node.put("leader", dept.getLeader());
                node.put("phone", dept.getPhone());
                node.put("email", dept.getEmail());
                node.put("status", dept.getStatus());
                
                List<Map<String, Object>> children = buildDeptTree(deptList, dept.getDeptId());
                if (!children.isEmpty()) {
                    node.put("children", children);
                }
                treeList.add(node);
            }
        }
        return treeList;
    }
}
