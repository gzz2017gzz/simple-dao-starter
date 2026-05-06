package com.hq.manage.sys.region;

import org.springframework.stereotype.Repository;

import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【地区】数据访问层
 * @date 2024-06-26 13:36:52
 **/
@Repository
public class RegionDao extends BaseDao<Region> {

    /**
     * 根据编号获取地区
     *
     * @param code 行政区编号
     * @return Region
     */
    public Region findByCode(Integer code) {
        return findOne(RegionCond.builder().regionCode(code).build());
    }
}