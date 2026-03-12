package com.keystone.web.controller.system;

import com.keystone.common.core.domain.Result;
import com.keystone.system.domain.SysMenu;
import com.keystone.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理控制器
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/api/system/menu")
public class SysMenuController {

    private final SysMenuService menuService;

    public SysMenuController(SysMenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "菜单列表")
    @GetMapping("/list")
    public Result<List<SysMenu>> list() {
        return Result.ok(menuService.selectMenuList());
    }

    @Operation(summary = "菜单树形结构")
    @GetMapping("/tree")
    public Result<List<SysMenu>> tree() {
        return Result.ok(menuService.selectMenuTree());
    }

    @Operation(summary = "菜单详情")
    @GetMapping("/{menuId}")
    public Result<SysMenu> getInfo(@PathVariable Long menuId) {
        return Result.ok(menuService.selectMenuById(menuId));
    }

    @Operation(summary = "新增菜单")
    @PostMapping
    public Result<Void> add(@RequestBody SysMenu menu) {
        menuService.insertMenu(menu);
        return Result.ok();
    }

    @Operation(summary = "修改菜单")
    @PutMapping
    public Result<Void> edit(@RequestBody SysMenu menu) {
        menuService.updateMenu(menu);
        return Result.ok();
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    public Result<Void> remove(@PathVariable Long menuId) {
        menuService.deleteMenuById(menuId);
        return Result.ok();
    }
}
