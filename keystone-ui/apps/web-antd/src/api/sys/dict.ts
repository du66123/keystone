import { requestClient } from '#/api/request';

// ========================
// 字典类型 API
// ========================

/**
 * 获取字典类型列表
 */
export function getDictTypeList(params?: any) {
  return requestClient.get('/system/dict/type/list', { params });
}

/**
 * 查询字典类型详细
 */
export function getDictTypeInfo(dictId: number) {
  return requestClient.get(`/system/dict/type/${dictId}`);
}

/**
 * 新增字典类型
 */
export function addDictType(data: any) {
  return requestClient.post('/system/dict/type', data);
}

/**
 * 修改字典类型
 */
export function updateDictType(data: any) {
  return requestClient.put('/system/dict/type', data);
}

/**
 * 删除字典类型
 */
export function deleteDictType(dictIds: string) {
  return requestClient.delete(`/system/dict/type/${dictIds}`);
}

/**
 * 获取字典选择框列表
 */
export function getDictOptionSelect() {
  return requestClient.get('/system/dict/type/optionselect');
}

// ========================
// 字典数据 API
// ========================

/**
 * 获取字典数据列表
 */
export function getDictDataList(params?: any) {
  return requestClient.get('/system/dict/data/list', { params });
}

/**
 * 查询字典数据详细
 */
export function getDictDataInfo(dictCode: number) {
  return requestClient.get(`/system/dict/data/${dictCode}`);
}

/**
 * 根据字典类型查询字典数据信息
 */
export function getDictDataByType(dictType: string) {
  return requestClient.get(`/system/dict/data/type/${dictType}`);
}

/**
 * 新增字典数据
 */
export function addDictData(data: any) {
  return requestClient.post('/system/dict/data', data);
}

/**
 * 修改字典数据
 */
export function updateDictData(data: any) {
  return requestClient.put('/system/dict/data', data);
}

/**
 * 删除字典数据
 */
export function deleteDictData(dictCodes: string) {
  return requestClient.delete(`/system/dict/data/${dictCodes}`);
}
