package com.hq.manage.sys.roledept;

import com.hq.common.aop.AutoLog;
import com.hq.common.config.Result;
import com.hq.manage.sys.dept.DeptCond;
import com.hq.manage.sys.dept.DeptService;
import com.hq.manage.sys.roledept.model.RoleDeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hq.common.config.Result.success;
import static com.hq.common.enums.sys.DeptStatus.ENABLE;

/**
 * @author 高振中
 * @类说明 【角色部门关联】控制器
 * @date 2024-05-10 21:44:15
 **/
@RestController
@RequestMapping("roleDept")
public class RoleDeptController {
    @Autowired
    private RoleDeptService roleDeptService; // 注入【角色部门关联】业务逻辑层
    @Autowired
    private DeptService deptService; // 注入【组织机构】业务逻辑层

    @AutoLog("【角色部门关联】-新增")
    @PostMapping("save")
    public Result<Void> save(@RequestBody List<RoleDept> list, Long roleId) {
        roleDeptService.insertBatch(list, roleId);
        return success();
    }

    @AutoLog("【角色部门关联】-查询【部门】树和选中的部门")
    @PostMapping("tree")
    public Result<RoleDeptVO> tree(Long roleId) {
        List<Long> ids = roleDeptService.list(roleId).stream().map(RoleDept::getDeptId).toList();
        return success(RoleDeptVO.builder().ids(ids).depts(deptService.tree(DeptCond.builder().status(ENABLE.getCode()).build())).build());
    }
}
