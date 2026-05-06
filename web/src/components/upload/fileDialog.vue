<template>
  <div>
    <el-dialog :fullscreen="fullscreen" append-to-body :visible.sync="dialogVisible" title="预览">
      <!-- 查看 -->
      <div v-if="currentFile">
        <div v-if="currentFileType === 'excel'" class="officeShow">
          <vue-office-excel :src="fileSrc" style="height: 100vh;width: 100%;" />
        </div>
        <div v-else-if="currentFileType === 'pdf'" class="officeShow">
          <vue-office-pdf :src="fileSrc" style="height: 100vh;width: 100%;" />
        </div>
        <div v-else-if="currentFileType === 'word'" class="officeShow">
          <vue-office-docx :src="fileSrc" style="height: 100vh;width: 100%;overflow: scroll;" />
        </div>
        <div v-else class="officeShow">
          <el-image :src="fileSrc" style="width: 100%"></el-image>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 引入VueOfficePdf组件
import VueOfficePdf from '@vue-office/pdf'
// docx
import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'
// 引入VueOfficeExcel组件
import VueOfficeExcel from '@vue-office/excel'
// 引入相关样式
import '@vue-office/excel/lib/index.css'

export default {
  components: {VueOfficePdf, VueOfficeDocx, VueOfficeExcel},
  data() {
    return {
      dialogVisible: false,
      currentFile: null,
      fullscreen:false,
      clientWidth: document.body.clientWidth
    }
  },
  watch:{
    clientWidth:{
      handler(val, oldVal) {
        if (val < 800) {
          this.fullscreen = true
        } else {
          this.fullscreen = false
        }
      },
      immediate: true
    }
  },
  computed: {
    currentFileType() {
      if (!this.currentFile) return
      let type = ''
      if (this.currentFile.name || this.currentFile.fileName) {
        const arr = this.currentFile.name && this.currentFile.name.split('.') || this.currentFile.fileName && this.currentFile.fileName.split('.')
        type = arr[arr.length - 1]
      }
      switch (true) {
        case ['xls', 'xlsx'].includes(type):
          return 'excel'
        case ['doc', 'docx'].includes(type):
          return 'word'
        case ['pdf'].includes(type):
          return 'pdf'
        default:
          return 'img'
      }
    },
    fileSrc() {
      return this.currentFile.url || this.currentFile.path
    },
  },
  methods:{
    open(file){
      this.dialogVisible = true
      this.currentFile = file
    }
  }
}
</script>

<style lang="scss" scoped>

</style>