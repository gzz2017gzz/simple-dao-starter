package com.hq.manage.sys.tableref;

import com.simple.common.base.BaseCondition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【表引用关系】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableRefCond extends BaseCondition {

    /* 默认条件↓ */
    private Long id; /* 主键 */
    private String mainTable; /* 主表表名 */
    private String mainInfo; /* 主表描述 */
    private String subTable; /* 子表表名 */
    private String subInfo; /* 子表描述 */
    private String refId; /* 引表外键名称 */
    private String delFlag; /* 删除标记字段名 */
    private byte dr; /* 删除标记 */
    private String mainTableEq; /* 主表表名 */
    private String subTableEq; /* 子表表名 */
    private String refIdEq; /* 引表外键名称 */
    private Long idNe; /* 主键 */

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("id =", id);
        and("main_table LIKE", mainTable, 3);
        and("main_info LIKE", mainInfo, 3);
        and("sub_table LIKE", subTable, 3);
        and("sub_info LIKE", subInfo, 3);
        and("ref_id LIKE", refId, 3);
        and("del_flag LIKE", delFlag, 3);
        and("dr=", dr);

        and("main_table=", mainTableEq);
        and("sub_table=", subTableEq);
        and("ref_id=", refIdEq);
        and("id<>", idNe);
    }
    /* 自定义条件↓ */
}
