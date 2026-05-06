package com.hq.manage.base.select;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

/**
 * @author 高振中
 * @类说明 【学年】查询条件
 * @date 2024-06-17 15:51:23
 **/
@Setter
@Getter
@Schema(description = "【下拉】查询条件")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectCond {
    @Schema(description = "ID字段")
    @NotBlank(message = "ID字段名必填")
    private String idField;

    @Schema(description = "名称字段")
    @NotBlank(message = "name字段名必填")
    private String nameField;

    @Schema(description = "表名")
    @NotBlank(message = "表名必填")
    private String tableName;

    @Schema(description = "条件")
    @Builder.Default
    private String where = Strings.EMPTY;

    @Schema(description = "排序")
    @Builder.Default
    private String order = Strings.EMPTY;

    public String fullQueryString() {
        return (this.idField + this.nameField + this.tableName + this.where + this.order).toLowerCase();
    }
}