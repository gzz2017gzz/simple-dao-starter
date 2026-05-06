package com.hq.manage.base.select;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "【下拉】VO")
@Getter
@Setter
public class SelectVO {

    @Schema(description = "下拉数据ID")
    private String id;

    @Schema(description = "下拉数据名称")
    private String name;
}
