package com.hq.manage.sys.tableref;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 高振中
 * @类说明 【字段配置】值对象
 * @date 2024-05-10 21:44:15
 **/
@Setter
@Getter
@NoArgsConstructor
public class FieldConfVo {
    private List<String> conds = new ArrayList<>(); /* 自定义条件集 */
    private List<String> grids = new ArrayList<>(); /* 自定义表格列集 */
    private Long userId; /* 用户ID */
    private String component; /* 组件名 */

}
