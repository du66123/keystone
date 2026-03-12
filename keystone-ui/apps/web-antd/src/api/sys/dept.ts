import { requestClient } from '#/api/request';

/**
 * 获取部门列表
 */
export function getDeptList(params?: any) {
  return requestClient.get('/system/dept/list', { params });
}

/**
 * 获取部门树
 */
export function getDeptTree(params?: any) {
  return requestClient.get('/system/dept/tree', { params });
}

/**
 * 查询部门详细
 */
export function getDeptInfo(deptId: number) {
  return requestClient.get(`/system/dept/${deptId}`);
}

/**
 * 新增部门
 */
export function addDept(data: any) {
  return requestClient.post('/system/dept', data);
}

/**
 * 修改部门
 */
export function updateDept(data: any) {
  return requestClient.put('/system/dept', data);
}

/**
 * 删除部门
 */
export function deleteDept(deptId: number) {
  return requestClient.delete(`/system/dept/${deptId}`);
}
