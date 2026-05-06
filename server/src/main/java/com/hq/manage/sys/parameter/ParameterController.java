package com.hq.manage.sys.parameter;

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
 * @类说明 【系统参数】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("parameter")
public class ParameterController {

    @Autowired
    private ParameterService parameterService; // 注入【系统参数】业务逻辑层

    @AutoLog("【系统参数】新增")
    @PostMapping("save")
    public Result<Long> save(@RequestBody @Validated Parameter parameter) {
        if (parameterService.exists(ParameterCond.builder().paramNameEq(parameter.getParamName()).build())) {
            return error("【系统参数名称】不能重复！");
        }
        if (parameterService.exists(ParameterCond.builder().paramKeyEq(parameter.getParamKey()).build())) {
            return error("【系统参数键】不能重复！");
        }
        return success(parameterService.save(parameter));
    }

    @AutoLog("【系统参数】删除")
    @PostMapping("delete")
    public Result<Integer> delete(@RequestBody Long[] ids) {
        return success(parameterService.delete(ids));
    }

    @AutoLog("【系统参数】修改")
    @PostMapping("update")
    public Result<Integer> update(@RequestBody @Validated Parameter parameter) {
        if (parameterService.exists(ParameterCond.builder().paramNameEq(parameter.getParamName()).idNe(parameter.getId()).build())) {
            return error("【系统参数名称】不能重复！");
        }
        if (parameterService.exists(ParameterCond.builder().paramKeyEq(parameter.getParamKey()).idNe(parameter.getId()).build())) {
            return error("【系统参数键】不能重复！");
        }
        return success(parameterService.update(parameter));
    }

    @AutoLog("【系统参数】分页列表")
    @PostMapping("page")
    public Result<Page<Parameter>> page(@RequestBody ParameterCond cond) {
        return success(parameterService.page(cond));
    }
    @AutoLog("【系统参数】按关键字查")
    @PostMapping("findByKey")
    public Result<String> findByKey(String key) {
        return success(parameterService.findByKey(key));
    }

}
