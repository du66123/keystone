import { requestClient } from '#/api/request';

/**
 * 获取菜单列表
 */
export async function getMenuListApi(params?: Record<string, any>) {
  return requestClient.get('/system/menu/list', { params });
}

/**
 * 获取菜单树形结构 (可能有特定接口，根据后端决定)
 */
export async function getMenuTreeApi() {
  return requestClient.get('/system/menu/tree');
}

/**
 * 获取菜单详情
 */
export async function getMenuInfoByIdApi(menuId: number) {
  return requestClient.get(`/system/menu/${menuId}`);
}

/**
 * 新增菜单
 */
export async function addMenuApi(data: Record<string, any>) {
  return requestClient.post('/system/menu', data);
}

/**
 * 修改菜单
 */
export async function editMenuApi(data: Record<string, any>) {
  return requestClient.put('/system/menu', data);
}

/**
 * 删除菜单
 */
export async function deleteMenuApi(menuId: number) {
  return requestClient.delete(`/system/menu/${menuId}`);
}
