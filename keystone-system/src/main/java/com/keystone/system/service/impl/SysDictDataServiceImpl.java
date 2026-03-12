package com.keystone.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.keystone.system.domain.SysDictData;
import com.keystone.system.domain.table.SysDictDataTableDef;
import com.keystone.system.mapper.SysDictDataMapper;
import com.keystone.system.service.ISysDictDataService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 字典 业务层处理
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements ISysDictDataService {

    @Override
    public Page<SysDictData> selectDictDataList(SysDictData dictData, Page<SysDictData> page) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SysDictDataTableDef.SYS_DICT_DATA.ALL_COLUMNS)
                .from(SysDictDataTableDef.SYS_DICT_DATA)
                .where(SysDictDataTableDef.SYS_DICT_DATA.DICT_TYPE.eq(dictData.getDictType(), StrUtil.isNotBlank(dictData.getDictType())))
                .and(SysDictDataTableDef.SYS_DICT_DATA.DICT_LABEL.like(dictData.getDictLabel(), StrUtil.isNotBlank(dictData.getDictLabel())))
                .and(SysDictDataTableDef.SYS_DICT_DATA.STATUS.eq(dictData.getStatus(), StrUtil.isNotBlank(dictData.getStatus())))
                .orderBy(SysDictDataTableDef.SYS_DICT_DATA.DICT_SORT.asc());
        return mapper.paginate(page, queryWrapper);
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        return mapper.selectListByQuery(QueryWrapper.create()
                .where(SysDictDataTableDef.SYS_DICT_DATA.STATUS.eq("0"))
                .and(SysDictDataTableDef.SYS_DICT_DATA.DICT_TYPE.eq(dictType))
                .orderBy(SysDictDataTableDef.SYS_DICT_DATA.DICT_SORT.asc()));
    }

    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return mapper.selectOneById(dictCode);
    }

    @Override
    public void deleteDictDataByIds(Long[] dictCodes) {
        mapper.deleteBatchByIds(Arrays.asList(dictCodes));
    }

    @Override
    public int insertDictData(SysDictData dictData) {
        return mapper.insertSelective(dictData);
    }

    @Override
    public int updateDictData(SysDictData dictData) {
        return mapper.update(dictData);
    }
}
