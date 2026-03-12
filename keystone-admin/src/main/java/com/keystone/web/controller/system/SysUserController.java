package com.keystone.web.controller.system;

import com.keystone.common.core.domain.Result;
import com.keystone.system.domain.SysUser;
import com.keystone.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理控制器
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/system/user")
public class SysUserController {

    private final SysUserService userService;
    private final PasswordEncoder passwordEncoder;

    public SysUserController(SysUserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "用户列表")
    @GetMapping("/list")
    public Result<List<SysUser>> list(SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        return Result.ok(list);
    }

    @Operation(summary = "用户详情")
    @GetMapping("/{userId}")
    public Result<SysUser> getInfo(@PathVariable Long userId) {
        return Result.ok(userService.selectUserById(userId));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public Result<Void> add(@RequestBody SysUser user) {
        if (!userService.checkUsernameUnique(user.getUsername())) {
            return Result.fail("用户名 '" + user.getUsername() + "' 已存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.insertUser(user);
        return Result.ok();
    }

    @Operation(summary = "修改用户")
    @PutMapping
    public Result<Void> edit(@RequestBody SysUser user) {
        userService.updateUser(user);
        return Result.ok();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userIds}")
    public Result<Void> remove(@PathVariable Long[] userIds) {
        userService.deleteUserByIds(userIds);
        return Result.ok();
    }

    @Operation(summary = "重置密码")
    @PutMapping("/resetPwd")
    public Result<Void> resetPwd(@RequestBody SysUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.resetPassword(user);
        return Result.ok();
    }
}
