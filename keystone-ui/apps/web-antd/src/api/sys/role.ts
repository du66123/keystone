import { requestClient } from '#/api/request';

/**
 * 分页获取角色列表
 */
export async function getRoleListApi(params: Record<string, any>) {
  return requestClient.get('/system/role/list', { params });
}

/**
 * 获取角色详情
 */
export async function getRoleInfoByIdApi(roleId: number) {
  return requestClient.get(`/system/role/${roleId}`);
}

/**
 * 新增角色
 */
export async function addRoleApi(data: Record<string, any>) {
  return requestClient.post('/system/role', data);
}

/**
 * 修改角色
 */
export async function editRoleApi(data: Record<string, any>) {
  return requestClient.put('/system/role', data);
}

/**
 * 删除角色
 */
export async function deleteRoleApi(roleIds: number[]) {
  return requestClient.delete(`/system/role/${roleIds.join(',')}`);
}
