package com.hq.manage.sys.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.simple.common.base.key.Const.INT_1;

/**
 * @author 高振中
 * @类说明 【请求时长统计】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class RequestService {

    private static final ConcurrentMap<String, Request> TEMP_MAP = new ConcurrentHashMap<>();
    @Autowired
    private RequestDao requestDao; // 注入【请求时长统计】数据访问层

    /**
     * @方法说明 请求进临时map
     */
    public void add(Request request) {
        String key = request.getPath();
        Request before = beforeRequest(request, key);
        // 计算方法
        request.setSumMilliSecond(before.getSumMilliSecond() + request.getCurrentMilliSecond());
        request.setMaxMilliSecond(Math.max(request.getCurrentMilliSecond(), before.getMaxMilliSecond()));
        request.setMinMilliSecond(Math.min(request.getCurrentMilliSecond(), before.getMinMilliSecond()));
        request.setSumCount(before.getSumCount() + INT_1);
        request.setAvgMilliSecond((float) request.getSumMilliSecond() / request.getSumCount());
        TEMP_MAP.put(key, request);
    }

    /**
     * @方法说明 取前一次的值
     */
    private Request beforeRequest(Request request, String key) {
        Request before = TEMP_MAP.get(key);
        if (Objects.nonNull(before)) {
            return before;
        }
        RequestCond requestCond = RequestCond.builder().pathEq(request.getPath()).appNameEq(request.getAppName()).build();
        Request req = requestDao.findOne(false,requestCond);
        if (Objects.nonNull(req)) { // 数据库中存在的情况
            return req;
        }
        return Request.builder().appName(request.getAppName()).path(request.getPath()).build();
    }

    private final static int interval = 20 * 60 * 1000;

    /**
     * @方法说明 每隔interval(20)分钟入库一次-可调整
     */
    @Scheduled(fixedDelay = interval, initialDelay = interval)
    public void save() {
        requestDao.replaceBatch(TEMP_MAP.values().stream().toList());
    }

    /**
     * @方法说明 【请求时长统计】列表
     */
    public List<Request> list(RequestCond cond) {
        cond.setOrders("path");
        return requestDao.list(cond);
    }

    /**
     * @方法说明 【请求时长统计】删除
     */
    public int delete(Object[] ids) {
        for (Object id : ids) {
            TEMP_MAP.remove(id.toString());
        }
        return requestDao.delete(ids);
    }
}
