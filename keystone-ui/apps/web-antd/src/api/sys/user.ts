import { requestClient } from '#/api/request';

/**
 * 分页获取用户列表
 */
export async function getUserListApi(params: Record<string, any>) {
  return requestClient.get('/system/user/list', { params });
}

/**
 * 获取用户详情
 */
export async function getUserInfoByIdApi(userId: number) {
  return requestClient.get(`/system/user/${userId}`);
}

/**
 * 新增用户
 */
export async function addUserApi(data: Record<string, any>) {
  return requestClient.post('/system/user', data);
}

/**
 * 修改用户
 */
export async function editUserApi(data: Record<string, any>) {
  return requestClient.put('/system/user', data);
}

/**
 * 删除用户
 */
export async function deleteUserApi(userIds: number[]) {
  return requestClient.delete(`/system/user/${userIds.join(',')}`);
}

/**
 * 重置密码
 */
export async function resetUserPwdApi(data: Record<string, any>) {
  return requestClient.put('/system/user/resetPwd', data);
}
