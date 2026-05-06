package com.hq.manage.sys.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【地区】业务逻辑层
 * @date 2024-06-26 13:36:52
 **/
@Service
public class RegionService {

    @Autowired
    private RegionDao regionDao; // 注入【地区】数据访问层

    /**
     * @方法说明 【地区】新增并反回主键
     */
    public Integer save(Region region) {
        return regionDao.save(region).getRegionCode();
    }

    /**
     * @方法说明 【地区】删除
     */
    public int delete(Object[] ids) {
        return regionDao.delete(ids);
    }

    /**
     * @方法说明 【地区】修改
     */
    public int update(Region region) {
        return regionDao.update(region);
    }

    /**
     * @方法说明 【地区】分页列表
     */
    public Page<Region> page(RegionCond cond) {
        return regionDao.page(cond);
    }

    /**
     * @方法说明 【地区】列表
     */
    public List<Region> list(RegionCond cond) {
        return regionDao.list(cond);
    }

    public String joinName(Integer provinceCode, Integer cityCode, String inSchoolName) {
        return inSchoolName;

        //下面先不要，有问题

//        if (!Strings.isBlank(inSchoolName)) {
//            return inSchoolName;
//        }
//
//        String provinceName = "", cityName = "";
//        if (Objects.nonNull(provinceCode)) {
//            Region province = regionDao.findByCode(provinceCode);
//            if (Objects.nonNull(province)) {
//                provinceName = province.getRegionName();
//            }
//        }
//        if (Objects.nonNull(cityCode)) {
//            Region city = regionDao.findByCode(cityCode);
//            if (Objects.nonNull(city)) {
//                cityName = city.getRegionName();
//            }
//        }
//
//        return provinceName + cityName + inSchoolName;
    }

}