<template>
  <div>
    <!-- 2.卡片视图 -->
    <el-card>
      <!-- 1.导航条 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>社区管理</el-breadcrumb-item>
        <el-breadcrumb-item>社区管理列表</el-breadcrumb-item>
      </el-breadcrumb>
      <!-- 2.1 顶部操作 -->
      <br />
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 使用clearable属性即可得到一个可清空的输入框   clear	在点击由 clearable 属性生成的清空按钮时触发-->
          <el-input
            placeholder="请输入社区名称"
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
          label="社区名称"
          prop="communityName"
          align="center"
        ></el-table-column>
        <el-table-column
          label="成立年份"
          prop="communityYear"
          align="center"
        ></el-table-column>
        <el-table-column
          label="占地面积/m^2"
          prop="communityCoverArea"
          align="center"
        ></el-table-column>
        <el-table-column
          label="楼栋数"
          prop="communityBuilding"
          align="center"
        ></el-table-column>
        <el-table-column label="单元数" prop="communityUnit" align="center"></el-table-column>
        <el-table-column
          label="总户数"
          prop="communityHolderSum"
          align="center"
        ></el-table-column>
        <el-table-column
          label="总人数"
          prop="communityPersonSum"
          align="center"
        ></el-table-column>
        <el-table-column label="操作" width="280px" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="showEditDialog(scope.row.id)"
            >编辑
            </el-button>
            <el-button
              size="mini"
              type="info"
              icon="el-icon-message"
              @click="showDetailDialog(scope.row.id)"
            >详情
            </el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
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
        :model="addCommunityForm"
        ref="addCommunityFormRef"
        :rules="addCommunityFormRules"
        label-width="120px"
      >
        <el-form-item label="社区名称" prop="communityName">
          <el-input v-model="addCommunityForm.communityName"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="成立年份" prop="communityYear">
              <el-date-picker
                v-model="addCommunityForm.communityYear"
                type="year"
                placeholder="选择年份"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="占地面积/m^2"
              prop="communityCoverArea"
              type="number"
            >
              <el-input
                v-model="addCommunityForm.communityCoverArea"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="楼栋数" prop="communityBuilding">
              <el-input v-model="addCommunityForm.communityBuilding"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单元数" prop="communityUnit">
              <el-input v-model="addCommunityForm.communityUnit"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="总户数" prop="communityHolderSum">
              <el-input
                v-model="addCommunityForm.communityHolderSum"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总人数" prop="communityPersonSum">
              <el-input
                v-model="addCommunityForm.communityPersonSum"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="其他内容" prop="communityOther">
          <el-input
            type="textarea"
            placeholder="请输入内容"
            v-model="addCommunityForm.communityOther"
          >
          </el-input>
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
        :model="editCommunityForm"
        ref="editCommunityFormRef"
        :rules="editCommunityFormRules"
        label-width="120px"
      >
        <el-form-item label="社区名称" prop="communityName">
          <el-input v-model="editCommunityForm.communityName"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="成立年份" prop="communityYear">
              <el-date-picker
                v-model="editCommunityForm.communityYear"
                type="year"
                placeholder="选择年份"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              label="占地面积/m^2"
              prop="communityCoverArea"
            >
              <el-input
                v-model="editCommunityForm.communityCoverArea"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="楼栋数" prop="communityBuilding">
              <el-input v-model="editCommunityForm.communityBuilding"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单元数" prop="communityUnit">
              <el-input v-model="editCommunityForm.communityUnit"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="总户数" prop="communityHolderSum">
              <el-input
                v-model="editCommunityForm.communityHolderSum"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="总人数" prop="communityPersonSum">
              <el-input
                v-model="editCommunityForm.communityPersonSum"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="其他内容" prop="communityOther">
          <el-input
            type="textarea"
            placeholder="请输入内容"
            v-model="editCommunityForm.communityOther"
          >
          </el-input>
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
        pagenum: 1,
        pagesize: 10,
      },

      multipleSelection: [], //多选  id

      list: [],
      total: 0,

      communityList: [],
      addDialogVisible: false,
      addCommunityForm: {
        communityName: "",
        communityYear: "",
        communityCoverArea: "",
        communityBuilding: "",
        communityUnit: "",
        communityHolderSum: "",
        communityPersonSum: "",
        communityOther: "",
      },

      addCommunityFormRules: {
        communityName: [
          { required: true, message: "请输入社区名称", trigger: "blur" },
          {
            min: 1,
            max: 40,
            message: "长度在 1 到 40 个字符",
            trigger: "blur",
          },
        ],
        communityYear: [
          { required: true, message: "请选择年份", trigger: "blur" },
        ],
        communityCoverArea: [
          { required: true, message: "请输入社区占地面积", trigger: "blur" },
          { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        ],
        communityBuilding: [
          { required: false, message: "请输入社区楼栋数", trigger: "blur" },
          { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        ],
        communityUnit: [
          { required: false, message: "请输入社区单元数", trigger: "blur" },
          { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        ],
        communityHolderSum: [
          { required: false, message: "请输入社区总户数", trigger: "blur" },
          { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        ],
        communityPersonSum: [
          { required: false, message: "请输入社区总人数", trigger: "blur" },
          { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        ],
      },

      editDialogVisible: false,

      editCommunityForm: {},
      editCommunityFormRules: {
        communityName: [
          { required: true, message: "请输入社区名称", trigger: "blur" },
          {
            min: 1,
            max: 40,
            message: "长度在 1 到 40 个字符",
            trigger: "blur",
          },
        ],
        communityYear: [
          { required: true, message: "请选择年份", trigger: "blur" },
        ],
        // communityCoverArea: [
        //   { required: true, message: "请输入社区占地面积", trigger: "blur" },
        //   { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        // ],
        // communityBuilding: [
        //   { required: false, message: "请输入社区楼栋数", trigger: "blur" },
        //   { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        // ],
        // communityUnit: [
        //   { required: false, message: "请输入社区单元数", trigger: "blur" },
        //   { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        // ],
        // communityHolderSum: [
        //   { required: false, message: "请输入社区总户数", trigger: "blur" },
        //   { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        // ],
        // communityPersonSum: [
        //   { required: false, message: "请输入社区总人数", trigger: "blur" },
        //   { min: 1, max: 11, message: "请输入数字", trigger: "blur" },
        // ],
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
      //{k1:v1,k2:v2}  ---> ?k1=v1&k2=v2    query=&pagenum=1&pagesize=10
      console.log(this.queryInfo, this.$qs.stringify(this.queryInfo));
      const { data: res } = await this.$http.post(
        "/community/list",
        this.$qs.stringify(this.queryInfo)
      );
      console.log(res);
      // debugger
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      this.list = res.data.records;
      this.total = res.data.total;
    },

    //添加
    showAddDialog() {
      this.addDialogVisible = true;
    },

    add() {
      this.$refs.addCommunityFormRef.validate(async (valid) => {
        console.log(valid);
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/community/save",
          this.addCommunityForm
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
      this.$refs.addCommunityFormRef.resetFields(); //清空表单
    },

    //编辑
    async showEditDialog(id) {
      console.log(id);
      const { data: res } = await this.$http.get("/community/info/" + id);

      console.log(res);

      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.editDialogVisible = true;
      this.editCommunityForm = res.data;
    },

    async showDetailDialog(id) {
      this.$router.push({ path: "/communityDetail", query: { id: id } });
    },

    async update() {
      this.$refs.editCommunityFormRef.validate(async (valid) => {
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/community/update",
          this.editCommunityForm
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
      this.$refs.editCommunityFormRef.resetFields();
    },

    removeById(ids) {
      if (ids.length == 0) return this.$message.error("请先选择至少一条");
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const { data: res } = await this.$http.post("/community/delete", ids);
          if (res.code !== 200) {
            return this.$message.error("删除信息失败！");
          }
          console.log(res);
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
