package com.keystone.web.controller.system;

import com.keystone.common.core.domain.Result;
import com.keystone.system.domain.SysDictData;
import com.keystone.system.service.ISysDictDataService;
import com.mybatisflex.core.paginate.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 */
@RestController
@RequestMapping("/api/system/dict/data")
@RequiredArgsConstructor
public class SysDictDataController {

    private final ISysDictDataService dictDataService;

    @GetMapping("/list")
    public Result<Page<SysDictData>> list(SysDictData dictData, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
        Page<SysDictData> page = new Page<>(pageNum, pageSize);
        Page<SysDictData> list = dictDataService.selectDictDataList(dictData, page);
        return Result.ok(list);
    }

    /**
     * 查询字典数据详细
     */
    @GetMapping(value = "/{dictCode}")
    public Result<SysDictData> getInfo(@PathVariable Long dictCode) {
        return Result.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public Result<List<SysDictData>> dictType(@PathVariable String dictType) {
        return Result.ok(dictDataService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典类型
     */
    @PostMapping
    public Result<Void> add(@Validated @RequestBody SysDictData dict) {
        dictDataService.insertDictData(dict);
        return Result.ok();
    }

    /**
     * 修改保存字典类型
     */
    @PutMapping
    public Result<Void> edit(@Validated @RequestBody SysDictData dict) {
        dictDataService.updateDictData(dict);
        return Result.ok();
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/{dictCodes}")
    public Result<Void> remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return Result.ok();
    }
}
