package com.hq.manage.sys.dictitem;

import java.time.LocalDateTime;

import com.simple.common.base.annotation.Exclude;
import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【字典项】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Table("sys_dict_item")
public class DictItem {
    // 字段↓
    @Id
    private Long id; // 主键
    private Long dictId; // 字典主键
    private String text; // 字典项文本
    private String itemValue; // 字典项值
    private String remark; // 备注
    private Float orderNum; // 排序编号
    private LocalDateTime createTime; /* 创建时间 */
    private LocalDateTime updateTime; /* 修改时间 */
    private Long createBy; /* 创建时间 */
    private Long updateBy; /* 创建时间 */
    private Byte dr; /* 删除标记 */
    // 扩展(显示)属性↓
    @Exclude
    private String code; // 字典编码
}
