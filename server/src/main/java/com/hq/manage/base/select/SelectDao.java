package com.hq.manage.base.select;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.hq.manage.sys.user.User;
import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【学年】数据访问层
 * @date 2024-06-17 15:51:23
 **/
@Repository
public class SelectDao extends BaseDao<User> {
    public List<SelectVO> selectList(SelectCond cond) {
        String sql = "SELECT " + cond.getIdField() + " id, " + cond.getNameField() + " name" + " FROM " + cond.getTableName();

        if (StringUtils.hasText(cond.getWhere())) {
            sql += " WHERE " + cond.getWhere();
        }

        if (StringUtils.hasText(cond.getOrder())) {
            sql += " ORDER BY " + cond.getOrder();
        }
        return list(sql, SelectVO.class);
    }
}