package com.hq.manage.sys.parameter;

import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【系统参数】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
public class ParameterCond extends BaseCondition {

    /* 默认条件↓ */
    private Long id; /* 主键 */
    private String groupName; /* 组名称 */
    private String paramName; /* 参数名称 */
    private String paramKey; /* 参数键 */
    private String paramValue; /* 参数值 */
    private byte dr; /* 删除标记 */
    /* 自定义条件↓ */
    private String paramNameEq; /* 参数名称等于 */
    private String paramKeyEq; /* 参数键等于 */
    private Long idNe; /* 主键 */

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("id=", id);
        and("group_name LIKE", groupName, 3);
        and("param_name LIKE", paramName, 3);
        and("param_key LIKE", paramKey, 3);
        and("param_value LIKE", paramValue, 3);
        and("dr =", dr);
        and("id<>", idNe);
        and("param_name=", paramNameEq);
        and("param_key=", paramKeyEq);
    }
}
