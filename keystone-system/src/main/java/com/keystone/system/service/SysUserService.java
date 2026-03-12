package com.keystone.system.service;

import com.keystone.system.domain.SysUser;
import com.keystone.system.mapper.SysUserMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.keystone.system.domain.table.SysUserTableDef.SYS_USER;

/**
 * 用户 Service
 */
@Service
public class SysUserService {

    private final SysUserMapper userMapper;

    public SysUserService(SysUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据用户名查询用户
     */
    public SysUser selectUserByUsername(String username) {
        return userMapper.selectOneByQuery(
                QueryWrapper.create().where(SYS_USER.USERNAME.eq(username))
                        .and(SYS_USER.DEL_FLAG.eq("0"))
        );
    }

    /**
     * 根据ID查询用户
     */
    public SysUser selectUserById(Long userId) {
        return userMapper.selectOneById(userId);
    }

    /**
     * 查询用户列表
     */
    public List<SysUser> selectUserList(SysUser user) {
        QueryWrapper query = QueryWrapper.create()
                .where(SYS_USER.DEL_FLAG.eq("0"));
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            query.and(SYS_USER.USERNAME.like(user.getUsername()));
        }
        if (user.getStatus() != null && !user.getStatus().isEmpty()) {
            query.and(SYS_USER.STATUS.eq(user.getStatus()));
        }
        if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            query.and(SYS_USER.PHONE.like(user.getPhone()));
        }
        if (user.getDeptId() != null) {
            query.and(SYS_USER.DEPT_ID.eq(user.getDeptId()));
        }
        return userMapper.selectListByQuery(query);
    }

    /**
     * 新增用户
     */
    public int insertUser(SysUser user) {
        return userMapper.insert(user);
    }

    /**
     * 修改用户
     */
    public int updateUser(SysUser user) {
        return userMapper.update(user);
    }

    /**
     * 删除用户 (逻辑删除)
     */
    public int deleteUserByIds(Long[] userIds) {
        int count = 0;
        for (Long userId : userIds) {
            SysUser user = new SysUser();
            user.setUserId(userId);
            user.setDelFlag("2");
            count += userMapper.update(user);
        }
        return count;
    }

    /**
     * 校验用户名唯一性
     */
    public boolean checkUsernameUnique(String username) {
        long count = userMapper.selectCountByQuery(
                QueryWrapper.create().where(SYS_USER.USERNAME.eq(username))
        );
        return count == 0;
    }

    /**
     * 重置密码
     */
    public int resetPassword(SysUser user) {
        return userMapper.update(user);
    }
}
