package com.keystone.system.service;

import com.keystone.common.constant.CacheConstants;
import com.keystone.common.exception.BusinessException;
import com.keystone.framework.redis.RedisCache;
import com.keystone.framework.security.JwtTokenProvider;
import com.keystone.system.domain.SysUser;
import com.keystone.system.domain.dto.LoginDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登录认证 Service
 */
@Service
public class SysLoginService {

    private final SysUserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisCache redisCache;

    public SysLoginService(SysUserService userService,
                           JwtTokenProvider jwtTokenProvider,
                           PasswordEncoder passwordEncoder,
                           RedisCache redisCache) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.redisCache = redisCache;
    }

    /**
     * 登录
     *
     * @return JWT Token
     */
    public String login(LoginDTO loginDTO) {
        // 查询用户
        SysUser user = userService.selectUserByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 校验状态
        if ("1".equals(user.getStatus())) {
            throw new BusinessException("用户已被停用");
        }

        // 校验密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }

        // 生成唯一 Key 标识本次登录会话
        String userKey = UUID.randomUUID().toString();

        // 构建 JWT Claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_key", userKey);
        claims.put("user_id", user.getUserId());
        claims.put("username", user.getUsername());

        // 缓存登录用户信息到 Redis
        redisCache.setCacheObject(
                CacheConstants.LOGIN_TOKEN_KEY + userKey,
                user,
                CacheConstants.TOKEN_EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );

        // 更新最后登录信息
        SysUser updateUser = new SysUser();
        updateUser.setUserId(user.getUserId());
        updateUser.setLoginDate(LocalDateTime.now());
        userService.updateUser(updateUser);

        return jwtTokenProvider.createToken(claims);
    }

    /**
     * 退出登录
     */
    public void logout(String token) {
        try {
            String userKey = jwtTokenProvider.getUserKey(token);
            if (userKey != null) {
                redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + userKey);
            }
        } catch (Exception ignored) {
            // Token 可能已过期，忽略
        }
    }
}
