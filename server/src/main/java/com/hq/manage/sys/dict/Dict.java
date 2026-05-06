package com.hq.manage.sys.dict;

import java.time.LocalDateTime;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 【枚举字典】实体
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_dict")
public class Dict {
    // 字段↓
    @Id
    private Long id; // 主键
    @NotBlank(message = "[字典名称]不能为空!")
    private String name; // 字典名称
    @NotBlank(message = "[字典编码]不能为空!")
    private String code; // 字典编码
    private String remark; // 备注
    private LocalDateTime createTime; /* 创建时间 */
    private LocalDateTime updateTime; /* 修改时间 */
    private Long createBy; /* 创建时间 */
    private Long updateBy; /* 创建时间 */
    private Byte dr; /* 删除标记 */
    // 扩展(显示)属性↓
}
