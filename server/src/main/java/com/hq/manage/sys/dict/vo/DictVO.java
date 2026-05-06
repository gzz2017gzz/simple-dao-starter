package com.hq.manage.sys.dict.vo;

import lombok.Data;


/**
 * @author 高振中
 * @类说明 【字典项】实体
 * @date 2024-05-10 21:45:31
 **/
@Data
public class DictVO {
    private String code; // 字典编码
    private String text; // 字典项文本
    private Long itemValue; // 字典项值
}
