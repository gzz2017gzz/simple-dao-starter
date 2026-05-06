package com.hq.manage.sys.dict;

import static com.hq.common.config.Result.error;
import static com.hq.common.config.Result.success;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.aop.CheckAdmin;
import com.hq.common.config.Result;
import com.hq.manage.sys.dictitem.DictItemCond;
import com.hq.manage.sys.dictitem.DictItemService;
import com.simple.common.base.Page;

import lombok.SneakyThrows;

/**
 * @author 高振中
 * @类说明 【枚举字典】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private DictService dictService; // 注入【字典】业务逻辑层
    @Autowired
    private DictItemService dictItemService; // 注入【字典项】业务逻辑层

    @AutoLog("【枚举字典】新增")
    @PostMapping("save")
    @CheckAdmin
    public Result<Long> save(@RequestBody @Validated Dict dict) {
        if (dictService.exists(DictCond.builder().codeEq(dict.getCode()).build())) {
            return error("【字典类型编码】不能重复！");
        }
        if (dictService.exists(DictCond.builder().nameEq(dict.getName()).build())) {
            return error("【字典类型名称】不能重复！");
        }
        return success(dictService.save(dict));
    }

    @AutoLog("【枚举字典】删除")
    @PostMapping("delete")
    @CheckAdmin
    public Result<Integer> delete(@RequestBody Long[] ids) {
        if (dictItemService.exists(DictItemCond.builder().dictIds(ids).build())) {
            return error("该类别存在【字典项】,不能删除！");
        }
        return success(dictService.delete(ids));
    }

    @AutoLog("【枚举字典】修改")
    @PostMapping("update")
    @CheckAdmin
    public Result<Integer> update(@RequestBody @Validated Dict dict) {
        if (dictService.exists(DictCond.builder().codeEq(dict.getCode()).idNe(dict.getId()).build())) {
            return error("【字典类型编码】不能重复！");
        }
        if (dictService.exists(DictCond.builder().nameEq(dict.getName()).idNe(dict.getId()).build())) {
            return error("【字典类型名称】不能重复！");
        }
        return success(dictService.update(dict));
    }

    @AutoLog("【枚举字典】分页列表")
    @PostMapping("page")
    public Result<Page<Dict>> page(@RequestBody DictCond cond) {
        return success(dictService.page(cond));
    }

    @AutoLog("【枚举字典】刷新")
    @PostMapping("refresh")
    @SneakyThrows
    public Result<String> refresh() {
        dictService.refresh();
        InetAddress inetAddress = InetAddress.getLocalHost();
        return success(inetAddress.getHostAddress());
    }
}
