package com.hq.manage.sys.user.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hq.manage.sys.dict.vo.DictVO;
import com.hq.manage.sys.func.Func;
import com.hq.manage.sys.user.User;

import lombok.Builder;
import lombok.Data;

/**
 * @author 高振中
 * @summary 登录登录响应值
 * @date 2024-05-10 21:45:31
 **/
@Data
@Builder
public class LoginVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private User user;// 用户信息
    private String token;// 令牌
    private Map<String, List<DictVO>> dicts;// 字典数据
    private List<Func> menus;// 菜单
    private List<Func> buttons;// 权限按钮
}
