package com.hq.manage.base.select;
import com.hq.common.aop.AutoLog;

import com.hq.common.config.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hq.common.config.Result.success;

@RestController
@RequestMapping("/select")
@Tag(name = "【下拉】API", description = "【下拉】接口")
public class SelectController {

    @Autowired
    private SelectService selectService;

    @Operation(summary = "【下拉】列表", description = "【下拉】列表")
    @PostMapping("/list")
    @AutoLog("【下拉】列表")
    public Result<List<SelectVO>> list(@Valid @RequestBody SelectCond cond) {
        return success(selectService.selectList(cond));
    }

}
