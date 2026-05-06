package com.hq.manage.sys.func;

import static com.simple.common.base.key.Const.*;
import static com.hq.common.enums.sys.LeafType.LEAF;
import static com.hq.common.enums.sys.LeafType.NO_LEAF;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hq.common.enums.sys.DrEnum;

/**
 * @author 高振中
 * @类说明 【功能菜单】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class FuncService {

    @Autowired
    private FuncDao funcDao; // 注入【功能菜单】数据访问层

    /**
     * @方法说明 【功能菜单】新增
     */
    public Long save(Func func) {
        if (!func.getParentId().equals(LONG_0)) {// 修改上级节点为非叶子
            funcDao.update(Func.builder().funcId(func.getParentId()).leaf(NO_LEAF.getCode()).build());
        }
        return funcDao.save(func).getFuncId();
    }

    /**
     * @方法说明 【功能菜单】删除
     */
    public void delete(Func func) {
        funcDao.delete(func.getFuncId());
        int count = funcDao.count(FuncCond.builder().parentId(func.getParentId()).dr(DrEnum.NORMAL.getCode()).build());
        if (count == INT_0) {// 修改上级节点为叶子
            funcDao.update(Func.builder().funcId(func.getParentId()).leaf(LEAF.getCode()).build());
        }
    }

    /**
     * @方法说明 【功能菜单】树
     */
    public List<Func> tree(FuncCond cond) {
        cond.setDr(DrEnum.NORMAL.getCode()).setOrders("func_id");
        List<Func> functions = funcDao.list(cond);
        Map<Long, Func> treeMap = new HashMap<>(INT_100);
        List<Func> treeList = new ArrayList<>();
        functions.forEach(func -> {
            treeMap.put(func.getFuncId(), func);
            if (func.getParentId() == LONG_0) { // 将根节点放入新的list
                treeList.add(func);
            } else if (!Objects.isNull(treeMap.get(func.getParentId()))) {// 将非根节点放入对应上级节点的children集合中
                treeMap.get(func.getParentId()).getChildren().add(func);
            }
        });
        treeList.forEach(func -> func.setChildren(func.getChildren().stream().sorted(Comparator.comparing(Func::getOrders, Comparator.nullsLast(Float::compare))).toList()));
        return treeList.stream().sorted(Comparator.comparing(Func::getOrders, Comparator.nullsLast(Float::compare))).toList();
    }

    /**
     * @方法说明 【功能菜单】更新
     */
    public int update(Func func) {
        return funcDao.update(func);
    }

    /**
     * @方法说明 【功能菜单】列表
     */
    public List<Func> list(FuncCond cond) {
        cond.setOrders("orders");
        return funcDao.list(cond);
    }

    /**
     * @方法说明 【功能菜单】是否存在
     */
    public boolean exists(FuncCond cond) {
        return funcDao.exists(cond);
    }

    /**
     * @方法说明 按主键查单个【功能菜单】
     */
    public Func findOne(Long id) {
        return funcDao.findById(id);
    }
}
