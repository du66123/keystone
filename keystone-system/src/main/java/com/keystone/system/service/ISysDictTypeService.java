package com.keystone.system.service;

import com.mybatisflex.core.paginate.Page;
import com.keystone.system.domain.SysDictType;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 * 字典 业务层
 */
public interface ISysDictTypeService extends IService<SysDictType> {
    
    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @param page 分页信息
     * @return 字典类型集合信息
     */
    Page<SysDictType> selectDictTypeList(SysDictType dictType, Page<SysDictType> page);

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    List<SysDictType> selectDictTypeAll();

    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    SysDictType selectDictTypeById(Long dictId);

    /**
     * 批量删除字典数据信息
     * 
     * @param dictIds 需要删除的字典ID
     * @return 结果
     */
    void deleteDictTypeByIds(Long[] dictIds);

    /**
     * 新增保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    int insertDictType(SysDictType dictType);

    /**
     * 修改保存字典类型信息
     * 
     * @param dictType 字典类型信息
     * @return 结果
     */
    int updateDictType(SysDictType dictType);

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dictType 字典类型
     * @return 结果
     */
    boolean checkDictTypeUnique(SysDictType dictType);
}
