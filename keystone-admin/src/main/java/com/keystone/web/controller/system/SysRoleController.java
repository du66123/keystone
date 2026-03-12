package com.keystone.web.controller.system;

import com.keystone.common.core.domain.Result;
import com.keystone.system.domain.SysRole;
import com.keystone.system.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器
 */
@Tag(name = "角色管理")
@RestController
@RequestMapping("/api/system/role")
public class SysRoleController {

    private final SysRoleService roleService;

    public SysRoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "角色列表")
    @GetMapping("/list")
    public Result<List<SysRole>> list(SysRole role) {
        return Result.ok(roleService.selectRoleList(role));
    }

    @Operation(summary = "角色详情")
    @GetMapping("/{roleId}")
    public Result<SysRole> getInfo(@PathVariable Long roleId) {
        return Result.ok(roleService.selectRoleById(roleId));
    }

    @Operation(summary = "新增角色")
    @PostMapping
    public Result<Void> add(@RequestBody SysRole role) {
        roleService.insertRole(role);
        return Result.ok();
    }

    @Operation(summary = "修改角色")
    @PutMapping
    public Result<Void> edit(@RequestBody SysRole role) {
        roleService.updateRole(role);
        return Result.ok();
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleIds}")
    public Result<Void> remove(@PathVariable Long[] roleIds) {
        roleService.deleteRoleByIds(roleIds);
        return Result.ok();
    }
}
