package com.hq.manage.sys.parameter;

import java.time.LocalDateTime;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【系统参数】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_parameter")
public class Parameter {
    // 字段↓
    @Id
    private Long id; /* 主键 */
    private String groupName; /* 组名称 */
    @NotBlank(message = "[参数名称]不能为空!")
    private String paramName; /* 参数名称 */
    @NotBlank(message = "[参数键]不能为空!")
    private String paramKey; /* 参数键 */
    @NotBlank(message = "[参数值]不能为空!")
    private String paramValue; /* 参数值 */
    private String remark; /* 备注 */
    private LocalDateTime createTime; /* 创建时间 */
    private Long createBy; /* 创建人ID */
    private LocalDateTime updateTime; /* 修改时间 */
    private Long updateBy; /* 修改人ID */
    private Byte dr; /* 删除标记 */
    /* 扩展(显示)属性↓@Exclude */
}
