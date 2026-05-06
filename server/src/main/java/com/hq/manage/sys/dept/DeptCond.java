package com.hq.manage.sys.dept;

import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 高振中
 * @类说明 【组织机构】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@Accessors(chain = true)
public class DeptCond extends BaseCondition {

    /* 默认条件↓ */
    private Long id; /* ID */
    private Long parentId; /* 上级ID */
    private String name; /* 机构名称 */
    private Byte type; /* 机构类型 1单位 2部门 */
    private String code; /* 机构编码 */
    private Byte status; /* 状态(1启用，0不启用) */
    private Float orderNum; /* 排序编号 */
    private Byte leaf; /* leaf */
    private String remark; /* 备注 */
    private byte dr; /* 删除标记 */
    private Object[] ids; /* 主键列表 */
    /* 自定义条件↓ */
    private String nameEq; /* 机构名称-等于 */
    private Long idNe; /* ID-不等于 */
    private Object[] typeIn; /* 机构类型 1单位 2部门 */
    private Object[] idNotIn;

    /**
     * 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("id =", id);
        notIn("id", idNotIn);
        and("parent_id =", parentId);
        and("name LIKE", name, 3);
        and("type =", type);
        in("type ", typeIn);
        and("code LIKE", code, 3);
        and("status =", status);
        and("order_num =", orderNum);
        and("leaf =", leaf);
        and("remark LIKE", remark, 3);
        and("dr =", dr);
        in("id", ids);
        and("name =", nameEq);
        and("id<>", idNe);
    }
}
