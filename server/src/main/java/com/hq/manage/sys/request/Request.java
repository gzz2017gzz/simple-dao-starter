package com.hq.manage.sys.request;

import com.simple.common.base.annotation.Exclude;
import com.simple.common.base.annotation.Id;
import com.simple.common.base.annotation.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【请求时长统计】实体
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("sys_request")
public class Request {
    // 字段↓
    @Id
    private String path; // 接口路径
    private String appName; // 应用名
    private int sumCount; // 请求次数
    private long sumMilliSecond; // 请求总时长
    private float avgMilliSecond; // 请求平均时长
    private long maxMilliSecond; // 请求最大时长
    @Builder.Default
    private long minMilliSecond = Integer.MAX_VALUE; // 请求最小时长
    private Byte type; // 1后台2前端
    // 扩展(显示)属性↓
    @Exclude
    private long currentMilliSecond;// 当前请求时长
}
