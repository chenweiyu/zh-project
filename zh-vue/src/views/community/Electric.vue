<template>
  <div>
    <!-- 2.卡片视图 -->
    <el-card>
      <!-- 1.导航条 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>社区管理</el-breadcrumb-item>
        <el-breadcrumb-item>住户用电管理列表</el-breadcrumb-item>
      </el-breadcrumb>
      <!-- 2.1 顶部操作 -->
      <br />

      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 使用clearable属性即可得到一个可清空的输入框   clear	在点击由 clearable 属性生成的清空按钮时触发-->
          <el-input
            placeholder="请输入住户名称"
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
        <el-col :span="5">
          <el-button
            type="primary"
            @click="showAddDialog"
            icon="el-icon-edit"
            plain
            >添加</el-button
          >
          <el-button
            type="danger"
            @click="removeById(multipleSelection)"
            icon="el-icon-delete"
            plain
            >批量删除</el-button
          >
        </el-col>
      </el-row>
      <br />
      <el-row>
        <span>通过月份进行查询用电情况：</span>
        <el-date-picker
          v-model="queryInfo.month"
          type="month"
          placeholder="选择月"
          @change="getList"
        >
        </el-date-picker>
      </el-row>
      <br />
      <!-- 2.2 列表区域  :data="list" -->
      <el-table
        ref="report-table"
        :data="list"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <!-- 索引列 -->
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
        <el-table-column
          label="户主姓名"
          prop="householderName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="住户月用电度数(度/月)"
          prop="electricNum"
          align="center"
        ></el-table-column>
        <el-table-column
          label="单价(元/度)"
          prop="electricPrice"
          align="center"
        ></el-table-column>
        <el-table-column
          label="总计(元)"
          prop="electricMoney"
          align="center"
        ></el-table-column>
        <el-table-column label="月份" prop="electricDate" align="center"> </el-table-column>
        <el-table-column label="操作" width="180px" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="showEditDialog(scope.row.id)"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="warning"
              @click="removeById([scope.row.id])"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <br />
      <!-- 2.3 分页 -->
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

    <!-- 3.弹框区域 -->

    <!-- 添加框 -->
    <el-dialog
      title="添加户主信息"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <el-form
        :model="addElectricForm"
        ref="addElectricFormRef"
        :rules="addElectricFormRules"
        label-width="120px"
      >
        <el-form-item label="户主姓名" prop="householderName">
          <el-input v-model="addElectricForm.householderName"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="住户月用电度数"
              prop="electricNum"
              type="number"
            >
              <el-input v-model="addElectricForm.electricNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="单价(元/度)"
              prop="electricPrice"
              type="number"
            >
              <el-input v-model="addElectricForm.electricPrice"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="月份" prop="electricDate">
          <el-date-picker
            v-model="addElectricForm.electricDate"
            type="month"
            placeholder="选择月"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="success" @click="add">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑框 -->
    <el-dialog
      title="编辑户主信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form
        :model="editElectricForm"
        ref="editElectricFormRef"
        :rules="editElectricFormRules"
        label-width="120px"
      >
        <el-form-item label="户主姓名" prop="householderName">
          <el-input v-model="editElectricForm.householderName" disabled></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="住户月用电度数"
              prop="electricNum"
              type="number"
            >
              <el-input v-model="editElectricForm.electricNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="单价(元/度)"
              prop="electricPrice"
              type="number"
            >
              <el-input v-model="editElectricForm.electricPrice"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="月份" prop="electricDate">
          <el-date-picker
            v-model="editElectricForm.electricDate"
            type="month"
            placeholder="选择月"
          >
          </el-date-picker>
        </el-form-item>
      </el-form>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<!-- 行为，业务逻辑 -->
<script>
export default {
  //变量
  data() {
    return {
      //封装的查询参数
      queryInfo: {
        query: "",
        month: "",
        pagenum: 1,
        pagesize: 10,
      },

      multipleSelection: [], //多选  id

      list: [],
      total: 0,

      electricList: [],
      addDialogVisible: false,
      addElectricForm: {
        householderName: "",
        electricNum: "",
        electricPrice: "",
        electricDate: "",
      },

      addElectricFormRules: {
        householderName: [
          { required: true, message: "请输入户主姓名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        electricNum: [
          { required: true, message: "请输入月用电度数", trigger: "blur" },
          { min: 1, max: 5, message: "请输入数字", trigger: "blur" },
        ],
        electricPrice: [
          { required: true, message: "请输入每度电的单价", trigger: "blur" },
          { min: 1, max: 5, message: "请输入数字", trigger: "blur" },
        ],
        electricDate: [
          { required: true, message: "请选择月份", trigger: "blur" },
        ],
      },

      editDialogVisible: false,

      editElectricForm: {},
      editElectricFormRules: {
        householderName: [
          { required: true, message: "请输入户主姓名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        householderNum: [
          { required: true, message: "请输入住户人数", trigger: "blur" },
          { min: 1, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        householderCommunity: [
          { required: true, message: "请选择作业类型", trigger: "blur" },
        ],
      },
    };
  },
  //生命函数
  created() {
    this.getList();
    this.getzylxList();
  },
  //方法
  methods: {
    // ////////////////////////////////////////////导出
    // exportExcel() {
    //   //按需加载
    //   import("@/assets/js/Export2Excel").then((excel) => {
    //     //表头
    //     const tHeader = ["学号", "姓名","作业名称"];
    //     //字段
    //     const filterVal = ["xh", "xm","hname"];
    //     //数据
    //     const list = this.list; //list为后台返回的json数据
    //     const data = this.formatJson(filterVal, list); //数据预处理
    //     excel.export_json_to_excel({
    //       header: tHeader,
    //       data,
    //       filename: "导出的数据", //导出表名称
    //     });
    //   });
    // },

    // //格式转换,不需要改动
    // formatJson(filterVal, jsonData) {
    //   return jsonData.map((v) => filterVal.map((j) => v[j]));
    // },

    //////////////////////////////////////////////////

    //获取字典类型
    async getzylxList() {
      // const { data: res } = await this.$http.get("/dict/showdict/zylx");

      // if (res.code !== 200) {
      //   return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      // }
      // console.log(res);
      // this.zylxList = res.data;
      // console.log(this.zylxList);
    },

    //查询  async  await
    async getList() {
      console.log("时间", this.queryInfo.month);
      console.log(this.queryInfo, this.$qs.stringify(this.queryInfo));
      const { data: res } = await this.$http.post(
        "/electric/list",
        this.$qs.stringify(this.queryInfo)
      );
      console.log(res);
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      this.list = res.data.records;
      for (var i = 0; i < this.list.length; i++) {
        this.list[i]["electricDate"] = this.list[i]["electricDate"].substr(0, 7);
      }
      this.total = res.data.total;
    },

    //添加
    showAddDialog() {
      this.addDialogVisible = true;
    },

    add() {
      this.$refs.addElectricFormRef.validate(async (valid) => {
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/electric/save",
          this.$qs.stringify(this.addElectricForm)
        );
        console.log(res);
        if (res.code !== 200) {
          return this.$message.error("添加信息失败！,错误信息为：" + res.msg);
        }
        this.$message.success("添加信息成功！");
        this.addDialogVisible = false;
        this.getList();
      });
    },

    // @close="addDialogClosed"\
    addDialogClosed() {
      console.log(this.$refs); //可以获取所有带ref属性的元素
      this.$refs.addElectricFormRef.resetFields(); //清空表单
    },

    //编辑
    async showEditDialog(id) {
      console.log(id);
      const { data: res } = await this.$http.get("/electric/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.editDialogVisible = true;
      this.editElectricForm = res.data;
      this.editElectricForm["id"] = id;
    },
    async update() {
      this.$refs.editElectricFormRef.validate(async (valid) => {
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/electric/update",
          this.$qs.stringify(this.editElectricForm)
        );
        if (res.code !== 200) {
          return this.$message.error("更新信息失败！错误信息为：" + res.msg);
        }
        this.$message.success("更新信息成功！");
        this.editDialogVisible = false;
        this.getList();
      });
    },
    editDialogClosed() {
      this.$refs.editElectricFormRef.resetFields();
    },

    removeById(ids) {
      console.log("ids", ids);
      if (ids.length == 0) return this.$message.error("请先选择至少一条");
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const { data: res } = await this.$http.post(
            "/electric/delete",
            ids
          );
          if (res.code !== 200) {
            return this.$message.error("删除信息失败！");
          }
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.getList();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    ////////////////////////////////////////
    // 多选,组成id数组
    handleSelectionChange(val) {
      console.log(val);
      //复制id到multipleSelection   map  [3,2,5,6,7]
      this.multipleSelection = val.map((x) => {
        return x.id;
      });
      console.log("选择的id：", this.multipleSelection);
    },
    //分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.queryInfo.pagesize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.queryInfo.pagenum = val; // 获取新页码
      this.getList();
    },
  },
};
</script>

<!-- css,样式scoped -->
<style scoped>
</style>
