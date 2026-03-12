package com.keystone.system.service;

import com.mybatisflex.core.paginate.Page;
import com.keystone.system.domain.SysDictData;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典数据 业务层
 */
public interface ISysDictDataService extends IService<SysDictData> {
    
    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @param page 分页信息
     * @return 字典数据集合信息
     */
    Page<SysDictData> selectDictDataList(SysDictData dictData, Page<SysDictData> page);

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    List<SysDictData> selectDictDataByType(String dictType);

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    SysDictData selectDictDataById(Long dictCode);

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     * @return 结果
     */
    void deleteDictDataByIds(Long[] dictCodes);

    /**
     * 新增保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    int insertDictData(SysDictData dictData);

    /**
     * 修改保存字典数据信息
     * 
     * @param dictData 字典数据信息
     * @return 结果
     */
    int updateDictData(SysDictData dictData);
}
