package com.hq.manage.sys.dictitem;

import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【字典项】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class DictItemCond extends BaseCondition {
    // 默认条件↓
    private byte dr; // 删除标记
    private Long dictId; // 字典主键
    private String text; // 字典项文本
    private String itemValue; // 字典项值
    // 自定义条件↓
    private Long idNe; // 字典主键不(等于)
    private String textEq; // 字典项文本(等于)
    private Object[] dictIds;// 主键数组
    private String code; // 字典编码

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("id <>", idNe);
        and("dict_id =", dictId);
        and("text LIKE", text, 3);
        and("item_value =", itemValue);
        and("text =", textEq);
        and("dr =", dr);
        in("dict_id", dictIds);

        add("AND d.code=?", code);
    }
}
