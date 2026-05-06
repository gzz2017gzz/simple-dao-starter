package com.hq.manage.sys.func;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simple.common.base.annotation.Exclude;
import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 高振中
 * @类说明 【功能菜单】实体
 * @date 2024-05-10 21:45:31
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_func")
public class Func {
    // 字段↓
    @Id
    private Long funcId; // 主键
    private Long parentId; // 所属上级
    private String name; // 名称
    private Byte type; // 类型(1:目录,2:菜单,3:按钮)
    private String path; // 路径
    private String param; // 参数
    private String icon; // 图标
    private Byte status; // 状态(0:禁止,1:正常)
    private Float orders; // 排序
    private String component; // 组件路径
    private Byte leaf; // 叶子1|0
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime; /* 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime; /* 修改时间 */
    private Byte dr; /* 删除标记 */
    private Long createBy; /* 创建时间 */
    private Long updateBy; /* 创建时间 */
    // 扩展(显示)属性↓
    @Exclude
    @Builder.Default
    private List<Func> children = new ArrayList<>();
}
