package com.hq.manage.sys.tableref;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【表引用关系】实体
 * @date 2024-05-10 21:45:31
 **/
@Setter
@Getter
@Table("sys_table_ref")
public class TableRef {
    // 字段↓
    @Id
    private Long id; /* 主键 */
    private String mainTable; /* 主表表名 */
    private String mainInfo; /* 主表描述 */
    private String subTable; /* 子表表名 */
    private String subInfo; /* 子表描述 */
    private String refId; /* 引表外键名称 */
    private String delFlag; /* 删除标记字段名 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime; /* 创建时间 */
    private Long createBy; /* 创建人id */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime; /* 修改时间 */
    private Long updateBy; /* 修改人id */
    private Byte dr; /* 删除标记 */
    /* 扩展(显示)属性↓@Exclude */
}
