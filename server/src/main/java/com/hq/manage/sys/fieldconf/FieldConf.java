package com.hq.manage.sys.fieldconf;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【字段配置】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_field_conf")
public class FieldConf {
    // 字段↓
    @Id
    private Long id; /* 主键 */
    private Long userId; /* 用户ID */
    private String component; /* 组件名 */
    private String cond; /* 自定义条件 */
    private String grid; /* 自定义表格 */
    private String remark; /* 备注 */
    /* 扩展(显示)属性↓@Exclude */
}
