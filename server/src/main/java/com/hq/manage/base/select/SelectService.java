package com.hq.manage.base.select;

import com.hq.manage.base.select.dto.TableMapCacheValue;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 高振中
 * @类说明 【下拉】业务逻辑层
 * @date 2024-06-17 15:51:23
 **/
@Service
public class SelectService {

    private static final Set<String> XSS_KEY = Set.of("grant", "exec", "insert", "delete", "update", "drop", "truncate", "alter", "create", "rename", "select", "master", "slave", "database", "mysql", "index", "*", ";");
    final private static Map<String, TableMapCacheValue> tableMapCache = new HashMap<>();
    @Resource
    private SelectDao selectDao;

    /**
     * @方法说明 【下拉】列表
     */
    public List<SelectVO> selectList(SelectCond cond) {
        String queryString = cond.fullQueryString();
        XSS_KEY.forEach(x -> Assert.isTrue(!queryString.contains(x), "v-select-table禁止的关键词:" + x));
        return selectDao.selectList(cond);
    }

    /**
     * 解析表字段
     *
     * @param table    表名
     * @param id       表id字段
     * @param name     表名称字段
     * @param originId 翻译值
     * @return String
     */
    public String parseTableText(String table, String id, String name, String originId) {
        TableMapCacheValue tableMapCacheValue = tableMapCache.get(table);
        if (Objects.nonNull(tableMapCacheValue) && tableMapCacheValue.getExpireTime().isAfter(LocalDateTime.now())) {
            return tableMapCacheValue.getData().getOrDefault(originId, originId);
        }

        List<SelectVO> tableList = selectList(SelectCond.builder().tableName(table).nameField(name).idField(id).build());
        Map<String, String> tableMap = tableList.stream().collect(Collectors.toMap(SelectVO::getId, SelectVO::getName, (v1, v2) -> v2));
        tableMapCache.put(table, TableMapCacheValue.builder().data(tableMap).expireTime(LocalDateTime.now().plusMinutes(5)).build());

        return tableMap.getOrDefault(originId, originId);
    }
}
