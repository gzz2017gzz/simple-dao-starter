package com.hq.manage.sys.tableref;

import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.config.Result;
import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【表引用关系】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("tableRef")
public class TableRefController {

    @Autowired
    private TableRefService tableRefService; // 注入【表引用关系】业务逻辑层

    @AutoLog("【表引用关系】新增")
    @PostMapping("save")
    public Result<Long> save(@RequestBody @Validated TableRef tableRef) {
        if (tableRefService.exists(TableRefCond.builder().mainTableEq(tableRef.getMainTable()).subTableEq(tableRef.getSubTable()).refIdEq(tableRef.getRefId()).build())) {
            return error("【主表表名,子表表名,外键名称】组合不能重复！");
        }
        return success(tableRefService.save(tableRef));
    }

    @AutoLog("【表引用关系】删除")
    @PostMapping("delete")
    public Result<Integer> delete(@RequestBody Long[] ids) {
        return success(tableRefService.delete(ids));
    }

    @AutoLog("【表引用关系】修改")
    @PostMapping("update")
    public Result<Integer> update(@RequestBody @Validated TableRef tableRef) {
        if (tableRefService.exists(TableRefCond.builder().mainTableEq(tableRef.getMainTable()).subTableEq(tableRef.getSubTable()).refIdEq(tableRef.getRefId()).idNe(tableRef.getId()).build())) {
            return error("【主表表名,子表表名,外键名称】组合不能重复！");
        }
        return success(tableRefService.update(tableRef));
    }

    @AutoLog("【表引用关系】分页列表")
    @PostMapping("page")
    public Result<Page<TableRef>> page(@RequestBody TableRefCond cond) {
        return success(tableRefService.page(cond));
    }

}
