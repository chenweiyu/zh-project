<template>
  <div>
    <!-- 2.卡片视图 -->
    <el-card>
      <!-- 1.导航条 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>系统管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
      </el-breadcrumb>

      <br />
      <el-row :gutter="20">
        <el-col :span="8">
          <!-- 使用clearable属性即可得到一个可清空的输入框   clear	在点击由 clearable 属性生成的清空按钮时触发-->
          <el-input
            placeholder="请输入用户名称"
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
      <el-table
        :data="list"
        border
        stripe
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column type="index" label="序号" width="60" align="center"></el-table-column>
        <el-table-column label="用户名" prop="username" width="120" align="center"></el-table-column>
        <el-table-column label="电话" prop="phone" width="180" align="center"></el-table-column>
        <el-table-column label="职位" prop="position" width="180" align="center"></el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="200" align="center"></el-table-column>
        <el-table-column label="角色" align="center">
          <template slot-scope="scope">
            <el-tag
              type="warning"
              v-for="(item, index) in scope.row.myRoleList"
              :key="index"
              style="margin: 5px;"
            >
              {{ item.roleName }}
            </el-tag>
          </template>
        </el-table-column>
        <!-- 自定义列 -->
        <el-table-column label="操作" width="200">
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
              type="danger"
              icon="el-icon-delete"
              @click="removeById([scope.row.id])"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <br/>
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

    <el-dialog
      title="添加"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <el-form
        :model="addForm"
        ref="addFormRef"
        :rules="addFormRules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="addForm.position"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>

        <el-form-item label="角色" prop="yesCheckList">
          <el-checkbox-group v-model="addForm.yesCheckList">
            <el-checkbox
              v-for="item in allCheckList"
              :label="item.id"
              :key="item.id"
              >{{ item.roleName }}</el-checkbox
            >
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="add">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑框 -->
    <el-dialog
      title="编辑角色"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form
        :model="editForm"
        ref="editFormRef"
        :rules="editFormRules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="editForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="editForm.password"
            placeholder="不更新密码，请不要输入信息"
          ></el-input>
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-input v-model="editForm.position"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="editForm.phone"></el-input>
        </el-form-item>

        <!--  v-model="editForm.yesCheckList" 双向绑定 -->
        <el-form-item label="角色" prop="yesCheckList">
          <el-checkbox-group v-model="editForm.yesCheckList">
            <el-checkbox
              v-for="item in allCheckList"
              :label="item.id"
              :key="item.id"
              >{{ item.roleName }}</el-checkbox
            >
          </el-checkbox-group>
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

<script>
export default {
  name: "User",
  data() {
    return {
      file: "",
      list: [],
      multipleSelection: [],
      queryInfo: {
        name: "",
        rid: "",
        pagenum: 1,
        pagesize: 10,
      },
      total: 0,
      allCheckList: [],
      addDialogVisible: false,
      addForm: {
        name: "",
        password: "",
        birthday: "",
        email: "",
        yesCheckList: [],
      },

      addFormRules: {
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 1,
            max: 25,
            message: "长度在 1 到 25 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 6,
            max: 8,
            message: "长度在 6 到 8 个字符",
            trigger: "blur",
          },
        ],
        yesCheckList: { required: true, message: "请选择", trigger: "blur" },
      },
      editForm: {},
      editDialogVisible: false,
      editFormRules: {
        name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          {
            min: 1,
            max: 25,
            message: "长度在 1 到 25 个字符",
            trigger: "blur",
          },
        ],
        yesCheckList: { required: true, message: "请选择", trigger: "blur" },
      },
    };
  },
  //生命函数
  created() {
    this.getList();
    this.getAllRoles();
  },
  methods: {
    ////////////////////添加
    add() {
      this.$refs.addFormRef.validate(async (valid) => {
        if (!valid) return;
        // 可以发起添加用户的网络请求
        //带图片，构造form
        let fd = new FormData();
        fd.append("file", this.file.raw);
        fd.append("user", JSON.stringify(this.addForm));

        const { data: res } = await this.$http.post("/user/save", fd);

        if (res.code !== 200) {
          return this.$message.error("添加信息失败！");
        }
        this.$message.success("添加信息成功！");
        this.addDialogVisible = false;
        this.getList();
      });
    },

    ///////////////////////////////////
    //查询所有角色
    async getAllRoles() {
      const { data: res } = await this.$http.post("/role/list");
      console.log(res);
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！");
      }
      this.allCheckList = res.data;
    },

    /// //////////////////// 获取所有角色的列表
    async getList() {
      console.log(this.$qs.stringify(this.queryInfo));
      const { data: res } = await this.$http.post(
        "/user/list",
        this.$qs.stringify(this.queryInfo)
      );
      console.log(res);
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！");
      }
      this.list = res.data.records;
      this.total = res.data.total;
    },

    /// ////////////////////编辑功能
    async showEditDialog(id) {
      console.log(id);
      const { data: res } = await this.$http.get("/user/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      console.log("-----", res.data);
      this.editForm = res.data; // 对象复制
      console.log("this.editForm", this.editForm);
      this.editDialogVisible = true;
    },

    ////////////////////更新
    update() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (!valid) return;
        //

        let fd = new FormData();
        fd.append("file", this.file.raw);
        fd.append("user", JSON.stringify(this.editForm));

        const { data: res } = await this.$http.post("/user/update", fd);
        if (res.code !== 200) {
          return this.$message.error("更新信息失败！");
        }

        // 提示修改成功
        this.$message.success("更新信息成功！");
        // 关闭对话框
        this.editDialogVisible = false;
        // 刷新数据列表
        this.getList();
      });
    },

    /// ////////////////////删除功能   ，实现批量删除
    removeById(ids) {
      console.log("-----", ids);
      if (ids.length == 0) return this.$message.error("请先选择至少一条");

      // 弹框
      this.$confirm("此操作将永久删除数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          // 删除逻辑
          const { data: res } = await this.$http.post("/user/delete", ids);

          if (res.code !== 200) {
            return this.$message.error("删除信息失败！" + res.msg);
          }

          this.$message.success("删除信息成功！");

          this.getList();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    ///////////////////////////////////
    // 多选,组成id数组
    handleSelectionChange(val) {
      console.log(val);
      //复制id到multipleSelection   map  [3,2,5,6,7]
      this.multipleSelection = val.map((x) => {
        return x.id;
      });
      console.log("选择的id：", this.multipleSelection);
    },
    /// ////////////////////分页方法
    // 监听 pagesize 改变的事件
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.queryInfo.pagesize = val;
      this.getList();
    },

    // 监听 页码值 改变的事件
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.queryInfo.pagenum = val; // 获取新页码
      this.getList();
    },

    /// ////////////////////添加功能
    showAddDialog() {
      this.addDialogVisible = true;
    },
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
      console.log("====", this.$refs.addFormRef);
    },
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
      console.log("****", this.$refs.editFormRef);
    },
  },
};
</script>

<style scoped>
.home {
  width: 1250px;
  margin-left: auto;
  margin-right: auto;
  margin-top: 20px;
}
#header-div {
  padding-bottom: 20px;
  padding-left: 5px;
  background-color: white;
}
#side-menu {
  position: fixed;
  margin-left: 50%;
  left: -680px;
  top: 100px;
}
</style>
