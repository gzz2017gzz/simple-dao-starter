/*组件demo,作者:keke,日期:2022-1-05*/
<template>
  <el-card>
    <el-form :inline="true">
      <el-form-item label="1.用户列表单选">
        <template> <el-input :placeholder="userName" size="mini" @click.native="showUserPage(2)" /> </template>
      </el-form-item><br />
      <el-form-item label="2.用户列表多选">
        <template>
          <el-input :placeholder="userNameMulti" :label="userNameMulti" size="mini" @click.native="showUserPageMulti(1)" />
        </template>
      </el-form-item><br />
      <el-form-item label="3.用户下拉列表单选">
        <v-user-select v-model="form.name" placeholder="请选择用户" clearable :deptId="'504670504792231946'" :addBlank="true" />
      </el-form-item><br />
      <el-form-item label="4.用户下拉列表多选">
        <v-user-select v-model="nameMulti" placeholder="请选择用户" clearable multiple :deptId="'504670504792231946'" :addBlank="true" />
      </el-form-item><br />
      <el-form-item label="5.部门树">
        <el-input placeholder="请选择机构" v-model="deptName" size="mini" @click.native="showDeptTree" />
      </el-form-item><br />
      <el-form-item label="6.文件上传">
        <v-upload :doImport="doImport" label="选择文件"></v-upload>
      </el-form-item><br />
      <el-form-item label="7.字典值下拉列表">
        <v-select v-model="form.gender" placeholder="请选择性别" dictKey="gender" :addBlank="true" />
      </el-form-item><br />
      <el-form-item label="8.权限按钮">
        <v-button buttonKey="person_save" label="权限按钮"></v-button>
      </el-form-item><br />
      <el-form-item label="9.表格">
      </el-form-item><br />
      <v-table :data="dataList" @selection-change="(rows) => selectChange(rows, 'id')" :loading="loading">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column type="expand">
          <template slot-scope="props">
            <span style="color: blue">请求参数</span> {{ props.row.params }}
            <hr />
            <span style="color: blue">反回结果</span> {{ props.row.result }}
          </template>
        </el-table-column>
        <el-table-column prop="title" label="日志标题"></el-table-column>
        <el-table-column prop="userId" label="用户主键"></el-table-column>
        <el-table-column prop="userName" label="用户名"></el-table-column>
        <el-table-column prop="ip" label="IP地址"></el-table-column>
        <el-table-column prop="method" label="java类.方法" width="280px"></el-table-column>
        <el-table-column prop="url" label="请求路径"></el-table-column>
        <el-table-column prop="requestType" label="请求类型"></el-table-column>
        <el-table-column prop="operateTime" label="请求时间" :formatter="dateFormat"></el-table-column>
        <el-table-column label="操作" width="50">
          <template slot-scope="props">
            <el-button type="text" size="mini" @click="() => $message.error('不许动,举起手来!')">删除</el-button>
          </template>
        </el-table-column>
      </v-table>
      <el-form-item label="10.分页条">
        <v-page :page="page" :total="total" :size="size" :pageChange="pageChange" :sizeChange="sizeChange"></v-page>
      </el-form-item><br />
    </el-form>
    <!-- 引入用户列表组件 -->
    <v-user-page ref="vuserpage" :setValue="setUserValue"></v-user-page>
    <v-user-page ref="userPageMulti" :setValue="setUserValueMulti"></v-user-page>
    <!-- 引入部门树组件 -->
    <v-dept-tree ref="deptTree" :setValue="setDeptValue"></v-dept-tree>
  </el-card>
</template>
<script>
import { pageMix } from "@/common/page";
export default {
  mixins: [pageMix],
  data() {
    return { total: 1, userName: "用户列表", userNameMulti: "用户列表", deptName: null, nameMulti: null, }
  },
  mounted() {
    this.loadData();
  },
  methods: {
    /*用户-查询参数*/
    initForm() { return { deptName: null, /*机构ID*/ name: null, /*姓名*/ gender: null, /*性别*/ }; },
    /* 用户列表选择单个 */
    showUserPage(type) {
      this.$refs.vuserpage.showUserPage(type);
    },
    /*返回用户信息*/
    setUserValue(user) {
      if (user != null) {
        this.userName = user.name;
      }
    },

    /* 用户列表选择多个 */
    showUserPageMulti(type) {
      this.$refs.userPageMulti.showUserPage(type);
    },
    /*返回用户信息*/
    setUserValueMulti(user) {
      if (user != null) {
        this.userNameMulti = "";
        user.filter(u => this.userNameMulti += u.name);
      }
    },

    /*文件上传*/
    doImport(obj) {
      /* this.rq.post("/role/import", obj.file).then((res) => {
        if (res.code == 200) this.loadData();
        else this.$message.error(res.msg);
      }); */
    },

    /*显示部门树*/
    showDeptTree() {
      this.$refs.deptTree.showTree();
    },
    /*返回部门节点*/
    setDeptValue(node) {
      this.deptName = node.name;
    },
    /*日志-分页列表*/
    loadData() {
      this.loading = true;
      const param = { ...this.form, page: this.page, size: this.size };
      this.rq.post("/log/page", param).then((res) => {
        if (res.code == 200) {
          this.dataList = res.data.dataList;
          this.total = res.data.rowCount;
          this.loading = false;
        } else this.$message.error(res.msg);
      });
    },
  },
};
</script>
<style></style>
