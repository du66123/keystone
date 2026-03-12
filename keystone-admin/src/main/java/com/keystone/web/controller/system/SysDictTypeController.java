package com.keystone.web.controller.system;

import com.keystone.common.core.domain.Result;
import com.keystone.system.domain.SysDictType;
import com.keystone.system.service.ISysDictTypeService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/api/system/dict/type")
@RequiredArgsConstructor
public class SysDictTypeController {

    private final ISysDictTypeService dictTypeService;

    @GetMapping("/list")
    public Result<Page<SysDictType>> list(SysDictType dictType, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<SysDictType> page = new Page<>(pageNum, pageSize);
        Page<SysDictType> list = dictTypeService.selectDictTypeList(dictType, page);
        return Result.ok(list);
    }

    /**
     * 查询字典类型详细
     */
    @GetMapping(value = "/{dictId}")
    public Result<SysDictType> getInfo(@PathVariable Long dictId) {
        return Result.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysDictType dict) {
        dictTypeService.insertDictType(dict);
        return Result.ok();
    }

    /**
     * 修改字典类型
     */
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysDictType dict) {
        dictTypeService.updateDictType(dict);
        return Result.ok();
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public Result<Void> remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return Result.ok();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public Result<List<SysDictType>> optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return Result.ok(dictTypes);
    }
}
