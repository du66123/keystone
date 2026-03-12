import type { UserInfo } from '@vben/types';

import { requestClient } from '#/api/request';

/**
 * 获取用户信息
 */
export async function getUserInfoApi() {
  // Keystone 在 /auth/info 返回 { user: {...}, permissions: [...] }
  const result: any = await requestClient.get('/auth/info');
  // 适配 Vben UserInfo 结构: username / realName / avatar 等
  return {
    ...result.user,
    realName: result.user.nickName || result.user.username,
    roles: result.user.username === 'admin' ? ['super'] : ['user'], 
  } as UserInfo;
}
