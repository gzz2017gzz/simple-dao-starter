package com.hq.manage.sys.region;

import com.simple.common.base.BaseCondition;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【地区】查询条件
 * @date 2024-06-26 13:36:52
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "【地区】查询条件")
public class RegionCond extends BaseCondition {
    /* 默认条件↓ */
    @Schema(description = "地区名称")
    private String regionName;
    @Schema(description = "地区级别 1-省、自治区、直辖市 2-地级市、地区、自治州、盟 3-市辖区、县级市、县")
    private Byte regionLevel;
    @Schema(description = "上级地区编号")
    private Integer parentCode;
    private Integer regionCode;

    /**
     * @方法说明 拼加条件
     **/
    @Override
    protected void addCondition() {
        and("region_name LIKE", regionName, 3);
        and("region_level =", regionLevel);
        and("parent_code =", parentCode);
        and("region_code =", regionCode);
    }
    /* 自定义条件↓ */
}