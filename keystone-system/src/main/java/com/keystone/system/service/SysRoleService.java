package com.keystone.system.service;

import com.keystone.system.domain.SysRole;
import com.keystone.system.mapper.SysRoleMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.keystone.system.domain.table.SysRoleTableDef.SYS_ROLE;

/**
 * 角色 Service
 */
@Service
public class SysRoleService {

    private final SysRoleMapper roleMapper;

    public SysRoleService(SysRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<SysRole> selectRoleList(SysRole role) {
        QueryWrapper query = QueryWrapper.create()
                .where(SYS_ROLE.DEL_FLAG.eq("0"));
        if (role.getRoleName() != null && !role.getRoleName().isEmpty()) {
            query.and(SYS_ROLE.ROLE_NAME.like(role.getRoleName()));
        }
        if (role.getStatus() != null && !role.getStatus().isEmpty()) {
            query.and(SYS_ROLE.STATUS.eq(role.getStatus()));
        }
        query.orderBy(SYS_ROLE.ORDER_NUM.asc());
        return roleMapper.selectListByQuery(query);
    }

    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectOneById(roleId);
    }

    public int insertRole(SysRole role) {
        return roleMapper.insert(role);
    }

    public int updateRole(SysRole role) {
        return roleMapper.update(role);
    }

    public int deleteRoleByIds(Long[] roleIds) {
        int count = 0;
        for (Long roleId : roleIds) {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            role.setDelFlag("2");
            count += roleMapper.update(role);
        }
        return count;
    }

    /**
     * 根据用户ID查询角色权限标识集合
     * (简化实现，阶段二后续可通过关联表查询)
     */
    public Set<String> selectRoleKeysByUserId(Long userId) {
        // TODO: 通过 sys_user_role 关联查询
        List<SysRole> roles = roleMapper.selectListByQuery(
                QueryWrapper.create().where(SYS_ROLE.DEL_FLAG.eq("0"))
                        .and(SYS_ROLE.STATUS.eq("0"))
        );
        return roles.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
    }
}
