package com.hq.manage.sys.func;

import com.simple.common.base.BaseCondition;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 高振中
 * @类说明 【功能菜单】查询条件
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@Accessors(chain = true)
public class FuncCond extends BaseCondition {
    // 默认条件↓
    private byte dr; /* 删除标记 */
    private Long funcId; // 主键
    private Long parentId; // 所属上级
    private String name; // 名称
    private Byte type; // 类型(1:目录,2:菜单,3:按钮)
    private String path; // 路径
    private String icon; // 图标
    private Byte status; // 状态(0:禁止,1:正常)
    private String component; // 文件路径
    private Object[] ids;// 主键数组

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("dr =", dr);
        and("func_id =", funcId);
        and("parent_id =", parentId);
        and("name LIKE", name, 3);
        and("type =", type);
        and("path LIKE", path, 3);
        and("icon LIKE", icon, 3);
        and("status =", status);
        and("component LIKE", component, 3);
        in("func_id", ids);
    }
    // 自定义条件↓
}
