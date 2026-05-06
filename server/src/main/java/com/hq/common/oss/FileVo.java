package com.hq.common.oss;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 【上传文件】响应信息
 *
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVo {
    private String url;// 展示路径
    private String name;// 原始文件名
    private String size;// 文件大小
}
