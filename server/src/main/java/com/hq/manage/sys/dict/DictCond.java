package com.hq.manage.sys.dict;

import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 【枚举字典】查询条件
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class DictCond extends BaseCondition {
    // 默认条件↓
    private Long id; // 主键
    private String name; // 字典名称
    private String code; // 字典编码
    private byte dr; // 删除标记
    // 自定义条件↓
    private Long idNe; // 主键
    private String nameEq; // 字典名称
    private String codeEq; // 字典编码

    /**
     * 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("dr =", dr);
        and("id =", id);
        and("name LIKE", name, 3);
        and("code LIKE", code, 3);
        and("name =", nameEq);
        and("code =", codeEq);
        and("id <>", idNe);
    }
}
