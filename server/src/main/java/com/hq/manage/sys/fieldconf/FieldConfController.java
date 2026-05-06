package com.hq.manage.sys.fieldconf;

import static com.hq.common.config.Result.success;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hq.common.aop.AutoLog;
import com.hq.common.config.Result;
import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【字段配置】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("fieldconf")
public class FieldConfController {
    @Autowired
    private FieldConfService fieldConfService; // 注入【字段配置】业务逻辑层

    @AutoLog("【字段配置】新增")
    @PostMapping("save")
    public Result<Long> save(@RequestBody FieldConfVo vo) {
        return success(fieldConfService.save(vo));
    }

    @AutoLog("【字段配置】删除")
    @PostMapping("delete")
    public Result<Integer> delete(@RequestBody Long[] ids) {
        return success(fieldConfService.delete(ids));
    }
    @AutoLog("【字段配置】分页列表")
    @PostMapping("page")
    public Result<Page<FieldConf>> page(@RequestBody FieldConfCond cond) {
        return success(fieldConfService.page(cond));
    }


    @AutoLog("【字段配置】列表")
    @PostMapping("list")
    public Result<FieldConfVo> list(@RequestBody FieldConfCond cond) {
        return success(fieldConfService.list(cond));
    }

}
