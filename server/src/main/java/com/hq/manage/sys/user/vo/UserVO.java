package com.hq.manage.sys.user.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "课程顾问信息")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    @Schema(description = "课程顾问ID")
    private Long userId;

    @Schema(description = "课程姓名")
    private String name;

    @Schema(description = "分管校部")
    private Byte manage;
}
