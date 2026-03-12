package com.keystone.web.controller;

import com.keystone.common.core.domain.Result;
import com.keystone.framework.config.properties.TokenProperties;
import com.keystone.framework.security.JwtTokenProvider;
import com.keystone.system.domain.SysMenu;
import com.keystone.system.domain.SysUser;
import com.keystone.system.domain.dto.LoginDTO;
import com.keystone.system.service.SysLoginService;
import com.keystone.system.service.SysMenuService;
import com.keystone.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 认证控制器
 */
@Tag(name = "认证管理")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final SysLoginService loginService;
    private final SysUserService userService;
    private final SysMenuService menuService;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenProperties tokenProperties;

    public AuthController(SysLoginService loginService,
                          SysUserService userService,
                          SysMenuService menuService,
                          JwtTokenProvider jwtTokenProvider,
                          TokenProperties tokenProperties) {
        this.loginService = loginService;
        this.userService = userService;
        this.menuService = menuService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenProperties = tokenProperties;
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginDTO loginDTO) {
        String token = loginService.login(loginDTO);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        return Result.ok("登录成功", data);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenProperties.getHeader());
        String token = jwtTokenProvider.resolveToken(bearerToken);
        if (token == null) {
            return Result.fail(401, "未登录");
        }

        Long userId = jwtTokenProvider.parseToken(token).get("user_id", Long.class);
        SysUser user = userService.selectUserById(userId);

        // 查询权限集合
        Set<String> permissions = menuService.selectMenuPermsByUserId(userId);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("user", user);
        data.put("permissions", permissions);
        return Result.ok(data);
    }

    @Operation(summary = "获取路由信息 (动态菜单)")
    @GetMapping("/routes")
    public Result<List<SysMenu>> getRoutes() {
        List<SysMenu> menus = menuService.selectMenuTree();
        return Result.ok(menus);
    }

    @Operation(summary = "退出登录")
    @DeleteMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenProperties.getHeader());
        String token = jwtTokenProvider.resolveToken(bearerToken);
        if (token != null) {
            loginService.logout(token);
        }
        return Result.ok("退出成功", null);
    }
}
