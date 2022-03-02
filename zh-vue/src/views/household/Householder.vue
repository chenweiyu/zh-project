<template>
  <div>
    <!-- 2.卡片视图 -->
    <el-card>
      <!-- 1.导航条 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>户主管理</el-breadcrumb-item>
        <el-breadcrumb-item>住户户主列表</el-breadcrumb-item>
      </el-breadcrumb>
      <!-- 2.1 顶部操作 -->
      <br />

      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 使用clearable属性即可得到一个可清空的输入框   clear	在点击由 clearable 属性生成的清空按钮时触发-->
          <el-input
            placeholder="请输入户主名称"
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
      <br/>
      <el-row>
        <span>通过社区名称查询社区户主：</span>
        <el-select
                v-model="queryInfo.householderCommunity"
                placeholder="请选择社区"
                @change="getSelectList"
              >
                <el-option
                  v-for="item in communityHolderList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
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
          width="120"
          align="center"
        ></el-table-column>
        <el-table-column
          label="住户人数"
          prop="householderNum"
          width="80"
          align="center"
        ></el-table-column>
        <el-table-column label="所在社区" prop="householderCommunity" align="center">
        </el-table-column>
        <el-table-column
          label="地址"
          prop="householderAddress"
          width="300"
          align="center"
        ></el-table-column>
        <el-table-column
          label="平均用水情况(度/月)"
          prop="householderWater"
          width="150"
          align="center"
        ></el-table-column>

        <el-table-column label="平均用电情况(度/月)" prop="householderElectric" width="150" align="center">
        </el-table-column>
        <el-table-column label="操作" width="180px" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="showEditDialog(scope.row.id)"
              circle
              plain
            >
            </el-button>
            <el-button
              size="mini"
              type="info"
              icon="el-icon-message"
              @click="showDetail(scope.row.id)"
              circle
              plain
            >
            </el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="removeById([scope.row.id])"
              circle
              plain
            >
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
        :model="addHouseholderForm"
        ref="addHouseholderFormRef"
        :rules="addHouseholderFormRules"
        label-width="100px"
      >
        <el-form-item label="户主姓名" prop="householderName">
          <el-input v-model="addHouseholderForm.householderName"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="住户人数" prop="householderNum">
              <el-input v-model="addHouseholderForm.householderNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在社区" prop="householderCommunity">
              <el-select
                v-model="addHouseholderForm.householderCommunity"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in communityList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="街道" prop="householderStreet">
          <el-input v-model="addHouseholderForm.householderStreet"></el-input>
        </el-form-item>
        <el-form-item label="单元室" prop="householderUnit">
          <el-input v-model="addHouseholderForm.householderUnit"></el-input>
        </el-form-item>
        <el-form-item label="门牌号" prop="householderHouseNum">
          <el-input v-model="addHouseholderForm.householderHouseNum"></el-input>
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
        :model="editHouseholderForm"
        ref="editHouseholderFormRef"
        :rules="editHouseholderFormRules"
        label-width="100px"
      >
        <el-form-item label="户主姓名" prop="householderName">
          <el-input v-model="editHouseholderForm.householderName"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="住户人数" prop="householderNum">
              <el-input v-model="editHouseholderForm.householderNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在社区" prop="householderCommunity">
              <el-select
                v-model="editHouseholderForm.householderCommunity"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in communityList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="街道" prop="householderStreet">
          <el-input v-model="editHouseholderForm.householderStreet"></el-input>
        </el-form-item>
        <el-form-item label="单元室" prop="householderUnit">
          <el-input v-model="editHouseholderForm.householderUnit"></el-input>
        </el-form-item>
        <el-form-item label="门牌号" prop="householderHouseNum">
          <el-input v-model="editHouseholderForm.householderHouseNum"></el-input>
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
        householderCommunity: "",
        pagenum: 1,
        pagesize: 10,
      },

      multipleSelection: [], //多选  id

      list: [],
      total: 0,

      communityList: [],
      communityHolderList: [],
      addDialogVisible: false,
      addHouseholderForm: {
        householderName: "",
        householderNum: "",
        householderCommunity: "",
        householderStreet: "",
        householderUnit: "",
        householderHouseNum: "",
      },

      addHouseholderFormRules: {
        householderName: [
          { required: true, message: "请输入户主姓名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
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

      editDialogVisible: false,

      editHouseholderForm: {},
      editHouseholderFormRules: {
        householderName: [
          { required: true, message: "请输入户主姓名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
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
    this.getAllCommunityName();
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
    async getAllCommunityName() {
      const { data: res } = await this.$http.post("/community/list");
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！");
      }
      for (var i = 0; i < res.data.length; i++) {
        this.communityList[i] = res.data[i]["communityName"];
        this.communityHolderList[i] = res.data[i]["communityName"];
      }
      this.communityHolderList.unshift("所有社区");
    },

    //获取字典类型
    async getzylxList() {
      // const { data: res } = await this.$http.get("/dict/showdict/zylx");

      // if (res.code !== 200) {
      //   return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      // }
      // this.zylxList = res.data;
    },

    //查询  async  await
    getSelectList () {
      this.queryInfo.pagenum = 1;
      this.queryInfo.pagesize = 10;
      this.getList();
    },

    async getList() {
      const { data: res } = await this.$http.post(
        "/householder/list",
        this.$qs.stringify(this.queryInfo)
      );
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      this.list = res.data.records;
      for (var i = 0; i < this.list.length; i++) {
        this.list[i]["householderAddress"] = "";
        if (this.list[i]["householder_street"] != "") {
          this.list[i]["householderAddress"] += this.list[i]["householder_street"] + "街道";
        }
        if (this.list[i]["householder_unit"] != "") {
          this.list[i]["householderAddress"] += this.list[i]["householder_unit"] + "单元";
        }
        if (this.list[i]["householder_house_num"] != "") {
          this.list[i]["householderAddress"] += "门牌" + this.list[i]["householder_house_num"] + "号";
        }
      }
      this.total = res.data.total;
    },

    //添加
    showAddDialog() {
      this.addDialogVisible = true;
    },

    add() {
      this.$refs.addHouseholderFormRef.validate(async (valid) => {
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/householder/save",
          this.$qs.stringify(this.addHouseholderForm)
        );
        if (res.code !== 200) {
          return this.$message.error("添加信息失败！,错误信息为：" + res.msg);
        }
        this.$message.success("添加户主信息成功！");
        this.addDialogVisible = false;
        this.getList();
      });
    },

    // @close="addDialogClosed"\
    addDialogClosed() {
      this.$refs.addHouseholderFormRef.resetFields(); //清空表单
    },

    //编辑
    async showEditDialog(id) {
      const { data: res } = await this.$http.get("/householder/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.editDialogVisible = true;
      this.editHouseholderForm = res.data;
      this.editHouseholderForm["id"] = id;
    },
    async update() {
      this.$refs.editHouseholderFormRef.validate(async (valid) => {
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/householder/update",
          this.$qs.stringify(this.editHouseholderForm)
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
      this.$refs.editHouseholderFormRef.resetFields();
    },

    showDetail(id) {
      this.$router.push({ path: "/householderDetail", query: { id: id } });
    },

    removeById(ids) {
      if (ids.length == 0) return this.$message.error("请先选择至少一条");

      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const { data: res } = await this.$http.post(
            "/householder/delete",
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
      //复制id到multipleSelection   map  [3,2,5,6,7]
      this.multipleSelection = val.map((x) => {
        return x.id;
      });
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
  },
};
</script>

<!-- css,样式scoped -->
<style scoped>
</style>
