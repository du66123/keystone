import type { RouteRecordStringComponent } from '@vben/types';

import { requestClient } from '#/api/request';

interface SysMenu {
  menuId: number;
  menuName: string;
  parentId: number;
  orderNum: number;
  path: string;
  component: string;
  isFrame: string;
  isCache: string;
  menuType: string;
  visible: string;
  status: string;
  perms: string;
  icon: string;
  children?: SysMenu[];
}

function mapSysMenuToVbenRoute(menuList: SysMenu[]): RouteRecordStringComponent[] {
  return menuList
    .filter((menu) => menu.menuType !== 'F')
    .map((menu) => {
    let component = menu.component;
    // Keystone 后端 'M' 代表目录, 'C' 代表菜单, 'F' 代表按钮
    // 如果是顶级目录，或者是明确的 'Layout'
    if (component === 'Layout' || (menu.parentId === 0 && (!component || component === ''))) {
      component = 'BasicLayout';
    } else if (!component && menu.menuType === 'M') {
      // 子目录如果没有指明组件，通常也是一个包裹层（嵌套路由使用中间件或者默认空白 router-view）
      // 但对于 Vben 我们可以复习它支持直接不传 component 或者提供一个空的 RouterView
      // 最安全的做法是在 Vben5 中传递 'BasicLayout' 并依靠其识别
      component = 'BasicLayout';
    } else if (component) {
      // 取消多余前缀，并确保返回的是一个以 '/' 开头的绝对路径风格字符串
      component = component.replace(/^(\.\/)?\/?views\//, '');
      if (component.endsWith('.vue')) {
        component = component.slice(0, -4);
      }
      if (!component.startsWith('/')) {
        component = `/${component}`;
      }
    }

    const route: RouteRecordStringComponent = {
      // name 必须唯一，使用 path 转换或直接用 path
      name: menu.path.replace(/^\//, '').replace(/\//g, '-') || `route-${menu.menuId}`,
      path:
        menu.parentId === 0
          ? menu.path.startsWith('/')
            ? menu.path
            : `/${menu.path}`
          : menu.path.replace(/^\//, ''),
      component: component || 'BasicLayout',
      meta: {
        title: menu.menuName,
        icon: menu.icon,
        order: menu.orderNum,
        hideMenu: menu.visible === '1', // '1' 在业务里可能代表隐藏，视前后端约定
        ignoreKeepAlive: menu.isCache === '1', 
      },
    };
    
    if (menu.children && menu.children.length > 0) {
      route.children = mapSysMenuToVbenRoute(menu.children);
    }
    
    return route;
  });
}

/**
 * 获取用户所有菜单
 */
export async function getAllMenusApi() {
  const result: any = await requestClient.get('/auth/routes');
  return mapSysMenuToVbenRoute(result || []);
}
