<template>
  <div>
    <el-card>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>字典列表</el-breadcrumb-item>
      </el-breadcrumb>
      <br />
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入名称"
            v-model="queryInfo.query"
            class="input-with-select"
            clearable
            @clear="getList"
          >
            <template #append>
              <el-button icon="el-icon-search" @click="getList"></el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="12">
          <el-button type="primary" @click="show('add')">添加</el-button>
          <el-button type="primary" @click="show('update')">编辑</el-button>
          <el-button type="primary" @click="removeById(multipleSelection)"
            >删除</el-button
          >
        </el-col>
      </el-row>

      <!-- 2.2 列表区域 -->
      <el-table
        :data="list"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <!-- 索引列 -->

        <el-table-column type="selection" width="60"></el-table-column>
        <el-table-column type="index" label="序号" width="60"></el-table-column>
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="类型" prop="type"></el-table-column>
        <el-table-column label="字典码" prop="code"></el-table-column>
        <el-table-column label="字典值" prop="value"></el-table-column>
        <el-table-column label="排序" prop="orderNum"></el-table-column>
        <el-table-column label="备注" prop="remark"></el-table-column>
      </el-table>

      <!-- 2.3 分页区域 -->

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-size="queryInfo.pagesize"
        :page-sizes="[5, 10, 20, 40]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>

    <!-- 演示抽屉,添加或编辑  :before-close    ref  closeDrawer()-->
    <el-drawer
      :title="title"
      :before-close="handleClose"
      :visible.sync="showDrawer"
      direction="rtl"
      ref="drawer"
    >
      <div style="padding-right: 10px">
        <el-form
          :model="saveForm"
          ref="saveFormRef"
          :rules="saveFormRules"
          label-width="100px"
        >
          <el-form-item label="名称" prop="name">
            <el-input v-model="saveForm.name"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-input v-model="saveForm.type"></el-input>
          </el-form-item>
          <el-form-item label="字典码" prop="code">
            <el-input v-model="saveForm.code"></el-input>
          </el-form-item>
          <el-form-item label="字典值" prop="value">
            <el-input v-model="saveForm.value"></el-input>
          </el-form-item>
          <el-form-item label="排序" prop="orderNum">
            <el-input-number v-model="saveForm.orderNum"></el-input-number>
          </el-form-item>
          <el-form-item label="摘要" prop="remark">
            <el-input v-model="saveForm.remark"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <!-- 底部 -->
      <div style="text-align: center">
        <el-button @click="$refs.drawer.closeDrawer()">取 消</el-button>
        <el-button type="primary" @click="save">提交</el-button>
      </div>
    </el-drawer>
  </div>
</template>
<script>
export default {
  //变量
  data() {
    return {
      //分页，封装的查询参数
      queryInfo: {
        query: "", //查询参数
        pagenum: 1,
        pagesize: 10,
      },
      total: 0,
      //表格
      list: [],
      multipleSelection: [], //多选  id

      //抽屉标题
      title: "",

      //抽屉
      showDrawer: false,
      //所有字段注解
      saveForm: {
        id: "",
        name: "",
        type: "",
        code: "",
        value: "",
        orderNum: 0,
        remark: "",
      },
      //验证规则
      saveFormRules: {
        name: [{ required: true, message: "请输入名称", trigger: "blur" }],
        type: [{ required: true, message: "请输入类型", trigger: "blur" }],
        code: [{ required: true, message: "请输入字典码", trigger: "blur" }],
        value: [{ required: true, message: "请输入字典值", trigger: "blur" }],
      },
    };
  },
  //生命函数
  created() {
    this.getList();
  },
  //方法
  methods: {
    // 多选,组成id数组
    handleSelectionChange(val) {
      console.log(val);
      //复制id到multipleSelection   map  [3,2,5,6,7]
      this.multipleSelection = val.map((x) => {
        return x.id;
      });
      console.log("选择的id：", this.multipleSelection);
    },

    //查询  async  await
    async getList() {
      const { data: res } = await this.$http.post(
        "/dict/list",
        this.$qs.stringify(this.queryInfo)
      );
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }

      this.list = res.data.records;

      this.total = res.data.total;
    },

    //分页
    handleSizeChange(val) {
      this.queryInfo.pagesize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      this.queryInfo.pagenum = val; // 获取新页码
      this.getList();
    },

    //添加
    async show(obj) {
      if (obj == "add") {
        this.title = "添加";
        console.log(obj);
      } else if ((obj = "update")) {
        console.log(obj);
        this.title = "更新";
        //走后台，获取要编辑的数据
        if (this.multipleSelection.length != 1)
          return this.$message.error("选择错误，请只选择一个");

        const { data: res } = await this.$http.get(
          "/dict/info/" + this.multipleSelection[0]
        );
        if (res.code !== 200) {
          return this.$message.error("查询信息失败！");
        }

        this.saveForm = res.data; // 对象复制
      }
      this.showDrawer = true; //展开抽屉
    },

    //提交后台
    save() {
      this.$refs.saveFormRef.validate(async (valid) => {
        console.log(valid);

        if (!valid) return;

        console.log(this.saveForm);

        //判断是更新（有id）或添加(无id)
        let url = "/dict/save";
        if (this.saveForm.id.length != 0) url = "/dict/update";

        const { data: res } = await this.$http.post(url, this.saveForm);

        console.log(res);

        if (res.code !== 200) {
          return this.$message.error("提交信息失败！,错误信息为：" + res.msg);
        }

        this.$message.success("提交信息成功！");

        this.$refs.drawer.closeDrawer(); //closeDrawer关闭抽屉，执行closeDrawer，回调handleClose

        this.getList();
      });
    },

    //执行closeDrawer，回调此函数
    handleClose(done) {
      this.$refs.saveFormRef.resetFields(); //清空表单
      this.showDrawer = false;
    },

    //删除
    removeById(ids) {
      console.log("-----", ids);
      if (ids.length == 0) return this.$message.error("请先选择至少一条");

      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const { data: res } = await this.$http.post("/dict/delete", ids);

          if (res.code !== 200) {
            return this.$message.error("删除信息失败！");
          }

          console.log(res);

          this.getList();

          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>
<style scoped>
.el-table {
  margin-top: 20px;
}

.el-pagination {
  margin-top: 20px;
  float: right;
}
</style>
