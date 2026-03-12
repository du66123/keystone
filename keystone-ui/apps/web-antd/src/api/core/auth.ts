import { baseRequestClient, requestClient } from '#/api/request';

export namespace AuthApi {
  /** 登录接口参数 */
  export interface LoginParams {
    password?: string;
    username?: string;
  }

  /** 登录接口返回值 */
  export interface LoginResult {
    accessToken: string;
  }

  export interface RefreshTokenResult {
    data: string;
    status: number;
  }
}

/**
 * 登录
 */
export async function loginApi(data: AuthApi.LoginParams) {
  const result: any = await requestClient.post('/auth/login', data);
  // Keystone 后端返回 { token: '...' }，Vben 需要 { accessToken: '...' }
  return { accessToken: result.token } as AuthApi.LoginResult;
}

/**
 * 刷新accessToken (暂时为空实现，待补充机制)
 */
export async function refreshTokenApi() {
  return baseRequestClient.post<AuthApi.RefreshTokenResult>('/auth/refresh', {
    withCredentials: true,
  });
}

/**
 * 退出登录
 */
export async function logoutApi() {
  // Keystone 是 DELETE /auth/logout
  return baseRequestClient.delete('/auth/logout');
}

/**
 * 获取用户权限码
 */
export async function getAccessCodesApi() {
  // Keystone 的权限码在 /auth/info 接口返回
  const result: any = await requestClient.get('/auth/info');
  return result.permissions as string[];
}
