package com.hq.manage.sys.dept;

import static com.simple.common.base.key.Const.INT_0;
import static com.simple.common.base.key.Const.INT_20;
import static com.simple.common.base.key.Const.LONG_0;
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
import org.springframework.transaction.annotation.Transactional;

import com.hq.common.enums.sys.DrEnum;

/**
 * @author 高振中
 * @类说明 【组织机构】业务逻辑层
 * @date 2024-05-10 21:44:15
 **/
@Service
public class DeptService {

    @Autowired
    private DeptDao deptDao; // 注入【组织机构】数据访问层

    /**
     * 【组织机构】树
     */
    public List<Dept> tree(DeptCond cond) {
        cond.setDr(DrEnum.NORMAL.getCode()).setOrders("id");
        List<Dept> deptList = deptDao.list(cond);// 查询所有记录
        Map<Long, Dept> treeMap = new HashMap<>(INT_20);
        List<Dept> treeList = new ArrayList<>();
        deptList.forEach(dept -> {
            treeMap.put(dept.getId(), dept);
            if (dept.getParentId() == LONG_0) {
                treeList.add(dept);
            } else if (!Objects.isNull(treeMap.get(dept.getParentId()))) {
                treeMap.get(dept.getParentId()).getChildren().add(dept);
            }
        });
        treeList.forEach(dept -> dept.setChildren(dept.getChildren().stream().sorted(Comparator.comparing(Dept::getOrderNum, Comparator.nullsLast(Float::compare))).toList()));
        return treeList.stream().sorted(Comparator.comparing(Dept::getOrderNum, Comparator.nullsLast(Float::compare))).toList();
    }

    /**
     * 【组织机构】新增
     */
    @Transactional(rollbackFor = Exception.class)
    public Long save(Dept dept) {
        dept.setLeaf(LEAF.getCode());
        if (!dept.getParentId().equals(LONG_0)) {// 修改上级节点为非叶子
            deptDao.update(Dept.builder().id(dept.getParentId()).leaf(NO_LEAF.getCode()).build());
        }
        return deptDao.replace(dept).getId();
    }

    /**
     * 删除不存在的
     */
    public void deleteNotInSync(Object[] allDepartIds) {
        if (Objects.isNull(allDepartIds) || allDepartIds.length == 0) {
            return;
        }
        deptDao.delete(DeptCond.builder().idNotIn(allDepartIds).build());
    }

    /**
     * 【组织机构】删除
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Dept dept) {
        deptDao.delete(dept.getId());
        int count = deptDao.count(DeptCond.builder().dr(DrEnum.NORMAL.getCode()).parentId(dept.getParentId()).build());
        if (count == INT_0) {// 修改上级节点为叶子
            deptDao.update(Dept.builder().id(dept.getParentId()).leaf(LEAF.getCode()).build());
        }
    }

    /**
     * 【组织机构】修改
     */
    public int update(Dept dept) {
        return deptDao.update(dept);
    }

    /**
     * 【组织机构】列表
     */
    public List<Dept> list(DeptCond cond) {
        cond.setOrders("order_num");
        return deptDao.list(cond);
    }

    /**
     * 【组织机构】是否存存
     */
    public boolean exists(DeptCond cond) {
        return deptDao.exists(cond);
    }

    /**
     * 按主键查询单个【组织机构】
     */
    public Dept findById(Long id) {
        return deptDao.findById(id);
    }
}
