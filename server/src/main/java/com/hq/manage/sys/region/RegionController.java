package com.hq.manage.sys.region;
import static com.hq.common.config.Result.success;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.config.Result;
import com.simple.common.base.Page;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("region")
@Tag(name = "【地区】API", description = "【地区】接口")
public class RegionController {

    @Autowired
    private RegionService regionService; // 注入【地区】业务逻辑层

    @PostMapping("save")
    @Operation(summary = "【地区】新增", description = "【地区】新增")
    @AutoLog("【地区】新增")
    public Result<Integer> save(@RequestBody @Validated Region region) {
        return success(regionService.save(region));
    }

    @PostMapping("delete")
    @Operation(summary = "【地区】删除", description = "参数格式:[1,2,3,4...]")
    @AutoLog("【地区】删除")
    public Result<Integer> delete(@RequestBody Long[] ids) {
        return success(regionService.delete(ids));
    }

    @PostMapping("update")
    @Operation(summary = "【地区】修改", description = "【地区】修改")
    @AutoLog("【地区】修改")
    public Result<Integer> update(@RequestBody @Validated Region region) {
        return success(regionService.update(region));
    }

    @PostMapping("page")
    @Operation(summary = "【地区】分页列表", description = "【地区】分页列表")
    @AutoLog("【地区】分页列表")
    public Result<Page<Region>> page(@RequestBody RegionCond cond) {
        return success(regionService.page(cond));
    }

    @Operation(summary = "【地区】列表", description = "【地区】列表")
    @PostMapping({"list", "out/list"})
    @AutoLog("【地区】列表")
    public Result<List<Region>> list(@RequestBody RegionCond cond) {
        return success(regionService.list(cond));
    }
}
