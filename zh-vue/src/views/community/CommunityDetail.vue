<template>
  <div>
    <el-card>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>社区管理</el-breadcrumb-item>
        <el-breadcrumb-item>社区信息详情</el-breadcrumb-item>
      </el-breadcrumb>
      <br />
      <div style="margin-top: 20px; margin-left: 10%">
        <span class="font-small">社区信息</span>
      </div>
      <div class="table-layout">
        <el-row>
          <el-col :span="4" class="table-cell-title">社区名称</el-col>
          <el-col :span="4" class="table-cell-title">成立年份</el-col>
          <el-col :span="4" class="table-cell-title">占地面积</el-col>
          <el-col :span="4" class="table-cell-title">楼栋数</el-col>
          <el-col :span="4" class="table-cell-title">总户数</el-col>
          <el-col :span="4" class="table-cell-title">总人数</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="table-cell">{{
            community.communityName
          }}</el-col>
          <el-col :span="4" class="table-cell">{{
            community.communityYear
          }}</el-col>
          <el-col :span="4" class="table-cell">{{
            community.communityCoverArea
          }}</el-col>
          <el-col :span="4" class="table-cell">{{
            community.communityBuilding
          }}</el-col>
          <el-col :span="4" class="table-cell">{{
            community.communityHolderSum
          }}</el-col>
          <el-col :span="4" class="table-cell">{{
            community.communityPersonSum
          }}</el-col>
        </el-row>
      </div>

      <el-card class="filter-container" shadow="never">
        <div>
          <i class="el-icon-search"></i>
          <span>筛选搜索</span>
          <el-button
            style="float: right"
            type="primary"
            @click="handleSearchList()"
            size="small"
          >
            查询搜索
          </el-button>
        </div>
        <div style="margin-top: 15px">
          <el-form
            :inline="true"
            :model="listQuery"
            size="small"
            label-width="140px"
          >
            <el-form-item label="住户名称：">
              <el-input
                v-model="listQuery.householdName"
                class="input-width"
                placeholder="住户名称"
              ></el-input>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <div class="table-container">
        <el-table
          ref="couponHistoryTable"
          :data="list"
          style="width: 88%"
          border
        >
          <el-table-column
            label="住户姓名"
            prop="householdName"
            align="center"
          ></el-table-column>
          <el-table-column
            label="联系方式"
            prop="householdPhone"
            align="center"
          ></el-table-column>
          <el-table-column
            label="性别"
            prop="householdSex"
            align="center"
          ></el-table-column>
          <el-table-column
            label="是否为户主"
            prop="householdIs"
            align="center"
          ></el-table-column>
          <el-table-column
            label="与户主关系"
            prop="householdRelation"
            align="center"
          ></el-table-column>
          <el-table-column
            label="籍贯"
            prop="householdNativePlace"
            align="center"
          >
          </el-table-column>
          <el-table-column
            label="出生日期"
            prop="householdBirth"
            align="center"
          >
          </el-table-column>
        </el-table>

        <br />
        <!-- 2.3 分页 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="listQuery.pagenum"
          :page-size="listQuery.pagesize"
          :page-sizes="[5, 10, 20, 40]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          class="pagination-css"
        >
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<!-- 行为，业务逻辑 -->
<script>
export default {
  data() {
    return {
      id: null,
      community: {},
      listQuery: {
        pagnum: 1,
        pagesize: 10,
        householdName: null,
        communityId: null,
      },
      householderAddress: "",
      list: [],
      total: 0,
    };
  },
  //生命函数
  created() {
    this.id = this.$route.query.id;
    this.listQuery.communityId = this.id;
    this.getCommunityList(this.id);
    this.getHousehold(this.id);
  },
  methods: {
    async getCommunityList(id) {
      const { data: res } = await this.$http.get("/community/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.community = res.data;
    },

    async getHousehold(id) {
      const { data: res } = await this.$http.post(
        "/household/sameCommunity/",
        this.$qs.stringify(this.listQuery)
      );
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.list = res.data.records;
      console.log("this.list", this.list);
    },

    handleSearchList() {
      this.listQuery.pageNum = 1;
      console.log("this.listQuery.householdName", this.listQuery.householdName);
      this.getHousehold();
    },

    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.listQuery.pagesize = val;
      this.getHousehold();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.listQuery.pagenum = val; // 获取新页码
      this.getHousehold();
    },
  },
};
</script>

<!-- css,样式scoped -->
<style scoped>
.operate-container {
  background: #f2f6fc;
  height: 80px;
  margin: -20px -20px 0;
  line-height: 80px;
}

.color-danger {
  margin-left: 20px;
  font-size: 20px;
}

.operate-button-container {
  float: right;
  margin-right: 20px;
}

.table-layout {
  margin-top: 20px;
  border-left: 1px solid #dcdfe6;
  border-top: 1px solid #dcdfe6;
  width: 80%;
  margin-left: 10%;
  margin-bottom: 30px;
}

.table-cell {
  height: 60px;
  line-height: 40px;
  border-right: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  padding: 10px;
  font-size: 14px;
  color: #606266;
  text-align: center;
  overflow: hidden;
}

.table-cell-title {
  border-right: 1px solid #dcdfe6;
  border-bottom: 1px solid #dcdfe6;
  padding: 10px;
  background: #f2f6fc;
  text-align: center;
  font-size: 14px;
  color: #303133;
}

.filter-container {
  margin: 20px 0 20px 10%;
  width: 80%;
}

.table-container {
  margin-left: 10%;
}

.pagination-css {
  float: right;
  margin-right: 12%;
}
</style>
