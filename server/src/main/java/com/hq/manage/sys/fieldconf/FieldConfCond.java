package com.hq.manage.sys.fieldconf;

import com.simple.common.base.BaseCondition;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【字段配置】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
public class FieldConfCond extends BaseCondition {

    /* 默认条件↓ */
    private Long id; /* 主键 */
    private Long userId; /* 用户ID */
    private String component; /* 组件名模糊 */
    private String componentEq; /* 组件名 */
    private String cond; /* 自定义条件 */
    private String grid; /* 自定义表格 */
    private String remark; /* 备注 */
    private Object[] ids; /* 主键列表 */

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("id =", id);
        and("user_id =", userId);
        and("component LIKE", component, 3);
        and("component =", componentEq);
        and("cond LIKE", cond, 3);
        and("grid LIKE", grid, 3);
        and("remark LIKE", remark, 3);
        in("id", ids);
    }
    /* 自定义条件↓ */
}
