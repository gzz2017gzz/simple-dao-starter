<template>
  <div>
    <el-upload
        :file-list="fileList"
        :action="action"
        :headers="headers"
        list-type="picture-card"
        :on-preview="handlePictureCardPreview"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :before-upload="beforeAvatarUpload"
        :multiple="true"
        v-bind="$attrs"
        :class="isAdd ? 'upload_img' : ''"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
    <el-dialog append-to-body :visible.sync="dialogVisible">
      <el-image :src="dialogImageUrl" :preview-src-list="[dialogImageUrl]" style="width: 100%"></el-image>
    </el-dialog>
  </div>
</template>
<script>
import Vue from "vue";
import {TOKEN} from "@/common/Constant";
const getFileName=(path)=>{
  if(path.lastIndexOf("\\")>=0){
    let reg=new RegExp("\\\\","g");
    path = path.replace(reg,"/");
  }
  return path.substring(path.lastIndexOf("/")+1);
}
export default {
  name: "uploadImg",
  data() {
    return {
      fileList:[],
      dialogImageUrl: '',
      dialogVisible: false,
      action: 'api/file/save',
      headers: {
        'token':Vue.ls.get(TOKEN)
      }
    };
  },
  props:{
    value: {
      type: Array,
      required:false
    },
    isAdd: {
      type: Boolean,
      default: false
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
    handleRefresh(deg){
      this.$refs.img.style.transform += `rotate(${deg})`;
    },

    beforeAvatarUpload(file) {
      const typeList = ['image/jpeg', 'image/png', 'image/gif', 'image/bmp', 'image/webp'];
      if (typeList.indexOf(file.type) < 0) {
        this.$message.error('上传图片格式不正确，请上传jpg/png/gif/bmp/webp格式的图片!');
        return false;
      }
    },

    handleRemove(file, fileList) {
      this.$emit('change',{
        fileList,
        fileType:1
      })
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleSuccess(response, file, fileList){
      if (response.code !== 200) return this.$message.error(response.msg);
      if (fileList.every(item => item.status === 'success')) {
        fileList.map(item => {
          item.response && this.fileList.push({name:item.name,path:item.response.data.url})
        })
        this.$emit('change',{
          fileList,
          fileType:1
        })
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.refresh-left {
  position: absolute;
  z-index: 1;
  left: 3%;
  top: 50%;
  background: rgba(241, 239, 239, 0.1);
}
.refresh-right {
  position: absolute;
  z-index: 1;
  right: 3%;
  top: 50%;
  background: rgba(241, 239, 239, 0.1);
}
::v-deep .upload_img .el-upload--picture-card {
  display: none !important;
}
</style>
