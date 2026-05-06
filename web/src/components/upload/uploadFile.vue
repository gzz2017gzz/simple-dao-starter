<template>
  <div>
    <!-- <el-dialog title="上传附件" :visible.sync="dialogFormVisible" width="50%" append-to-body="true"> -->
    <el-upload
        ref="upload"
        class="upload-demo"
        :action="action"
        :headers="headers"
        :before-remove="beforeRemove"
        multiple
        :on-preview="handlePreview"
        :file-list="fileList"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
        accept="image/*,.pdf,.docx,.xlsx,.doc,.xls"
        :on-remove="handleRemove"
        :limit="6"
        :on-exceed="handleExceed">
      <el-button size="small" type="primary">选取附件</el-button>
    </el-upload>
    <file-dialog ref="FileDialog"></file-dialog>
  </div>
</template>

<script>
import '@vue-office/excel/lib/index.css'
import Vue from "vue";
import {TOKEN} from "@/common/Constant";
import FileDialog from "@/components/upload/fileDialog";

export default {
  components: {
    FileDialog,
  },
  props: {
    projectId: {
      type: String,
      default: ''
    },
    value:{
      type:Array,
      default:[]
    }
  },
  data() {
    return {
      dialogFormVisible: false,
      action: 'api/file/save',
      fileList: [],
      currentFile: null,
      files: [],
      headers: {
        'token':Vue.ls.get(TOKEN)
      },
      dialogVisible:false
    }
  },
  watch:{
    value:{
      handler(val) {
        if (val.length === 0 || !val) {
          this.fileList = [];
        } else {
          this.fileList = val.map(item => {
            return {
              name: item.fileName,
              url: item.path,
              fileType:1
            }
          })
        }
      },
      immediate:true,
      deep:true,
    }
  },
  methods: {
    show() {
      this.dialogFormVisible = true
    },
    cancellation() {
      this.dialogFormVisible = false
      this.fileList = []
    },
    handleRemove(file, fileList) {
      this.fileList = fileList
      // 判断溢出的文件是否当前预览中的文件
      // if (fileList.findIndex(item => item.uid === this.currentFile.uid) === -1) {
      //   this.currentFile = null
      // }
      this.$emit('change',{
        fileList,
        fileType:2
      })
    },
    handlePreview(file) {
      this.$refs.FileDialog.open(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 6 个文件，本次选择了 ${files.length}个文件，共选择了 ${files.length + fileList.length}个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name} ？`)
    },
    handleSuccess(response, file, fileList) {
      this.files.push(response)
      if (response.code !== 200) return this.$message.error(response.msg);
      if (fileList.every(item => item.status === 'success')) {
        fileList.map(item => {
          item.response && this.fileList.push({name:item.name,path:item.response.data.url})
        })
        this.$emit('change',{
          fileList,
          fileType:2
        })
      }
    },
    beforeUpload(file) {
      const isLt20M = file.size / 1024 / 1024 < 20
      if (!isLt20M) {
        this.$message.error('上传文件大小不能超过 20MB!')
      }
      return isLt20M
    }
  }
}
</script>

<style lang="scss" scoped>
.pdf-container {
  width: 100%;
  height: 600px; /* 或者其他适当的高度 */
}
</style>