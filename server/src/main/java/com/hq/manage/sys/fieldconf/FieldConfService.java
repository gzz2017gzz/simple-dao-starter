package com.hq.manage.sys.fieldconf;

import static com.simple.common.base.key.Const.Sql.COMMA;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.common.base.Page;

/**
 * @author 高振中
 * @类说明 【字段配置】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class FieldConfService {

    @Autowired
    private FieldConfDao fieldConfDao; // 注入【字段配置】数据访问层

    /**
     * @方法说明 【字段配置】新增并反回主键
     */
    public Long save(FieldConfVo vo) {
        String cond = String.join(COMMA, vo.getConds());
        String grid = String.join(COMMA, vo.getGrids());
        FieldConf fieldConf = FieldConf.builder().userId(vo.getUserId()).cond(cond).grid(grid).component(vo.getComponent()).build();
        return fieldConfDao.replace(fieldConf).getId();
    }

    /**
     * @方法说明 【字段配置】删除
     */
    public int delete(Object[] ids) {
        return fieldConfDao.delete(ids);
    }

    /**
     * @方法说明 【字段配置】分页列表
     */
    public Page<FieldConf> page(FieldConfCond cond) {
        return fieldConfDao.page(cond);
    }

    /**
     * @方法说明 【字段配置】列表
     */
    public FieldConfVo list(FieldConfCond cond) {
        FieldConf config = fieldConfDao.list(cond).stream().findFirst().orElse(null);
        return Objects.nonNull(config) ? new FieldConfVo(config.getCond().split(","), config.getGrid().split(",")) : new FieldConfVo();
    }
}
