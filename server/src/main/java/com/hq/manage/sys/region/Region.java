package com.hq.manage.sys.region;

import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【地区】实体
 * @date 2024-06-26 13:36:52
 **/
@Setter
@Getter
@Schema(description = "【地区】实体")
@Table("sys_region")
public class Region {
    // 字段↓
    @Id
    @Schema(description = "地区编号")
    private Integer regionCode;

    @Schema(description = "地区名称")
    private String regionName;

    @Schema(description = "地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县")
    private Byte regionLevel;

    @Schema(description = "上级地区编号")
    private Integer parentCode;

    /* 扩展(显示)属性↓@Exclude */
}