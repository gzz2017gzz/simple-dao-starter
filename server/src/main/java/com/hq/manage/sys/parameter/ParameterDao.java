package com.hq.manage.sys.parameter;

import static org.springframework.util.Assert.isTrue;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【系统参数】数据访问层
 * @date 2024-05-10 21:44:15
 **/
@Repository
public class ParameterDao extends BaseDao<Parameter> {

    public String findByKey(String key) {
        ParameterCond cond = ParameterCond.builder().paramKeyEq(key).build();
        Parameter parameter = findOne(cond);
        isTrue(Objects.nonNull(parameter), String.format("没有配置参数键%s ", key));
        return parameter.getParamValue();
    }
}
