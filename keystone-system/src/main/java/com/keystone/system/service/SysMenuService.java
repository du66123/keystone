package com.keystone.system.service;

import com.keystone.system.domain.SysMenu;
import com.keystone.system.mapper.SysMenuMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.keystone.system.domain.table.SysMenuTableDef.SYS_MENU;

/**
 * 菜单 Service
 */
@Service
public class SysMenuService {

    private final SysMenuMapper menuMapper;

    public SysMenuService(SysMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    /**
     * 查询菜单列表
     */
    public List<SysMenu> selectMenuList() {
        return menuMapper.selectListByQuery(
                QueryWrapper.create()
                        .where(SYS_MENU.STATUS.eq("0"))
                        .orderBy(SYS_MENU.PARENT_ID.asc(), SYS_MENU.ORDER_NUM.asc())
        );
    }

    /**
     * 查询菜单树形结构
     */
    public List<SysMenu> selectMenuTree() {
        List<SysMenu> menus = selectMenuList();
        return buildMenuTree(menus, 0L);
    }

    /**
     * 构建菜单树
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> menus, Long parentId) {
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (parentId.equals(menu.getParentId())) {
                menu.setChildren(buildMenuTree(menus, menu.getMenuId()));
                tree.add(menu);
            }
        }
        return tree;
    }

    /**
     * 查询权限标识集合
     */
    public Set<String> selectMenuPermsByUserId(Long userId) {
        // TODO: 阶段二通过 user_role + role_menu 关联查询
        List<SysMenu> menus = menuMapper.selectListByQuery(
                QueryWrapper.create().where(SYS_MENU.STATUS.eq("0"))
                        .and(SYS_MENU.MENU_TYPE.in("C", "F"))
        );
        return menus.stream()
                .map(SysMenu::getPerms)
                .filter(p -> p != null && !p.isEmpty())
                .collect(Collectors.toSet());
    }

    public SysMenu selectMenuById(Long menuId) {
        return menuMapper.selectOneById(menuId);
    }

    public int insertMenu(SysMenu menu) {
        return menuMapper.insert(menu);
    }

    public int updateMenu(SysMenu menu) {
        return menuMapper.update(menu);
    }

    public int deleteMenuById(Long menuId) {
        return menuMapper.deleteById(menuId);
    }
}
