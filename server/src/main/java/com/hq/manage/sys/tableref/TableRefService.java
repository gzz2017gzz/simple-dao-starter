package com.hq.manage.sys.tableref;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【表引用关系】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class TableRefService {

    @Autowired
    private TableRefDao tableRefDao; // 注入【表引用关系】数据访问层

    /**
     * @方法说明 【表引用关系】新增并反回主键
     */
    public Long save(TableRef tableRef) {
        return tableRefDao.save(tableRef).getId();
    }

    /**
     * @方法说明 【表引用关系】删除
     */
    public int delete(Object[] ids) {
        return tableRefDao.delete(ids);
    }

    /**
     * @方法说明 【表引用关系】修改
     */
    public int update(TableRef tableRef) {
        return tableRefDao.update(tableRef);
    }

    /**
     * @方法说明 【表引用关系】分页列表
     */
    public Page<TableRef> page(TableRefCond cond) {
        return tableRefDao.page(cond);
    }

    /**
     * @方法说明 【表引用关系】个数
     */
    public boolean exists(TableRefCond cond) {
        return tableRefDao.exists(cond);
    }
}
