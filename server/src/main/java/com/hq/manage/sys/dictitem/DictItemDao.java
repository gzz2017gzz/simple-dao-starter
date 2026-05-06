package com.hq.manage.sys.dictitem;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hq.manage.sys.dict.vo.DictVO;
import com.simple.common.base.BaseDao;

/**
 * @author 高振中
 * @类说明 【字典项】数据访问层
 * @date 2024-05-10 21:44:15
 **/
@Repository
public class DictItemDao extends BaseDao<DictItem> {

    /**
     * @方法说明 【字典项】全部列表
     */
    public List<DictVO> listDict( ) {
        String sb = "SELECT d.code,t.item_value,t.text,t.order_num FROM sys_dict_item t JOIN sys_dict d ON d.id=t.dict_id WHERE t.dr=0 AND d.dr=0" +
 
                " ORDER BY code,order_num";
        return list(sb, DictVO.class );
    }

    /**
     * @方法说明 【字典项】全部列表
     */
    public List<DictVO> listItem(DictItemCond cond) {
        String SQL = "SELECT d.code,t.item_value,t.text,t.order_num FROM sys_dict_item t JOIN sys_dict d ON d.id=t.dict_id WHERE t.dr=0 AND d.dr=0";
        return list(SQL + cond.and(), DictVO.class, cond.array());
    }
}
