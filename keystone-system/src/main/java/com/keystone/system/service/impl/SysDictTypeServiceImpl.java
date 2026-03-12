package com.keystone.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.keystone.common.exception.BusinessException;
import com.keystone.system.domain.SysDictType;
import com.keystone.system.domain.table.SysDictTypeTableDef;
import com.keystone.system.mapper.SysDictTypeMapper;
import com.keystone.system.service.ISysDictTypeService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 字典 业务层处理
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Override
    public Page<SysDictType> selectDictTypeList(SysDictType dictType, Page<SysDictType> page) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SysDictTypeTableDef.SYS_DICT_TYPE.ALL_COLUMNS)
                .from(SysDictTypeTableDef.SYS_DICT_TYPE)
                .where(SysDictTypeTableDef.SYS_DICT_TYPE.DICT_NAME.like(dictType.getDictName(), StrUtil.isNotBlank(dictType.getDictName())))
                .and(SysDictTypeTableDef.SYS_DICT_TYPE.STATUS.eq(dictType.getStatus(), StrUtil.isNotBlank(dictType.getStatus())))
                .and(SysDictTypeTableDef.SYS_DICT_TYPE.DICT_TYPE.like(dictType.getDictType(), StrUtil.isNotBlank(dictType.getDictType())));
        return mapper.paginate(page, queryWrapper);
    }

    @Override
    public List<SysDictType> selectDictTypeAll() {
        return mapper.selectAll();
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return mapper.selectOneById(dictId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictTypeByIds(Long[] dictIds) {
        mapper.deleteBatchByIds(Arrays.asList(dictIds));
    }

    @Override
    public int insertDictType(SysDictType dictType) {
        if (!checkDictTypeUnique(dictType)) {
            throw new BusinessException("字典类型已存在");
        }
        return mapper.insertSelective(dictType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDictType(SysDictType dictType) {
        if (!checkDictTypeUnique(dictType)) {
            throw new BusinessException("字典类型已存在");
        }
        return mapper.update(dictType);
    }

    @Override
    public boolean checkDictTypeUnique(SysDictType dictType) {
        Long dictId = StrUtil.isNullOrUndefined(dictType.getDictId() != null ? dictType.getDictId().toString() : null) ? -1L : dictType.getDictId();
        SysDictType info = mapper.selectOneByQuery(QueryWrapper.create().where(SysDictTypeTableDef.SYS_DICT_TYPE.DICT_TYPE.eq(dictType.getDictType())));
        if (info != null && info.getDictId().longValue() != dictId.longValue()) {
            return false;
        }
        return true;
    }
}
