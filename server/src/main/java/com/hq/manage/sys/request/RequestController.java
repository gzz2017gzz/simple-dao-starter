package com.hq.manage.sys.request;

import com.hq.common.aop.AutoLog;
import com.hq.common.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hq.common.config.Result.success;

/**
 * @author 高振中
 * @类说明 【请求时长统计】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("request")
public class RequestController {

    @Autowired
    private RequestService requestService; // 注入【请求时长统计】业务逻辑层

    @AutoLog("【请求时长统计】删除")
    @PostMapping("delete")
    public Result<Integer> delete(@RequestBody String[] ids) {
        return success(requestService.delete(ids), "删除成功");
    }

    @AutoLog("【请求时长统计】列表")
    @PostMapping("list")
    public Result<List<Request>> list(@RequestBody RequestCond cond) {
        return success(requestService.list(cond));
    }
}
