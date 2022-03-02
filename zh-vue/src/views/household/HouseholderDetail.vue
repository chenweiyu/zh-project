<template>
  <div>
    <el-card>
      <div class="operate-container">
        <span class="color-danger">户主信息详情</span>
      </div>
      <div style="margin-top: 40px; margin-left: 10%;">
        <span class="font-small">基本信息</span>
      </div>
      <div class="table-layout">
        <el-row>
          <el-col :span="4" class="table-cell-title">户主姓名</el-col>
          <el-col :span="4" class="table-cell-title">家庭人数</el-col>
          <el-col :span="6" class="table-cell-title">所在社区</el-col>
          <el-col :span="10" class="table-cell-title">住户地址</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="table-cell">{{ householder.householderName }}</el-col>
          <el-col :span="4" class="table-cell">{{ householder.householderNum }}</el-col>
          <el-col :span="6" class="table-cell">{{ householder.householderCommunity }}</el-col>
          <el-col :span="10" class="table-cell">{{ householderAddress }}</el-col>
        </el-row>
      </div>

      <div style="margin-top: 60px; margin-left: 10%;">
        <span class="font-small">户主信息</span>
      </div>
      <div class="table-layout" style="margin-bottom: 30px;">
        <el-row>
          <el-col :span="4" class="table-cell-title">户主姓名</el-col>
          <el-col :span="4" class="table-cell-title">联系方式</el-col>
          <el-col :span="4" class="table-cell-title">性别</el-col>
          <el-col :span="4" class="table-cell-title">出生日期</el-col>
          <el-col :span="4" class="table-cell-title">籍贯</el-col>
          <el-col :span="4" class="table-cell-title">是否军人家庭</el-col>
        </el-row>
        <el-row>
          <el-col :span="4" class="table-cell">{{this.household.householdName}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdPhone}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdSex}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdBirth}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdNativePlace}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdSoldierFam}}</el-col>
        </el-row>
        <el-row>
          <el-col :span="6" class="table-cell-title">身份证号</el-col>
          <el-col :span="5" class="table-cell-title">户籍所在地</el-col>
          <el-col :span="4" class="table-cell-title">政治面貌</el-col>
          <el-col :span="4" class="table-cell-title">退役军人</el-col>
          <el-col :span="5" class="table-cell-title">是否为烈士家属</el-col>
        </el-row>
        <el-row>
          <el-col :span="6" class="table-cell">{{this.household.householdIdentify}}</el-col>
          <el-col :span="5" class="table-cell">{{this.household.householdHjPlace}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdOutlook}}</el-col>
          <el-col :span="4" class="table-cell">{{this.household.householdVeterans}}</el-col>
          <el-col :span="5" class="table-cell">{{this.household.householdMartyrFam}}</el-col>
        </el-row>
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
      householder: {},
      householderAddress: "",
      household: {},
    };
  },
  //生命函数
  created() {
    this.id = this.$route.query.id;
    this.getHouseholderDetail(this.id);
  },
  methods: {
    async getHouseholderDetail(id) {
      const { data: res } = await this.$http.get("/householder/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.householder = res.data;
      if (this.householder.householderStreet != null) {
        this.householderAddress += this.householder.householderStreet + "街道";
      }
      if (this.householder.householderUnit != null) {
        this.householderAddress += this.householder.householderUnit + "单元";
      }
      if (this.householder.householderHouseNum != null) {
        this.householderAddress += this.householder.householderHouseNum + "号";
      }
      this.getHouseholdDetail(this.householder.householdId);
    },

    async getHouseholdDetail(id) {
      console.log("id", id);
      const { data: res } = await this.$http.get("/household/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.household = res.data;
      console.log("this.household", this.household);
    },

  }
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
    border-left: 1px solid #DCDFE6;
    border-top: 1px solid #DCDFE6;
    width: 80%;
    margin-left: 10%;
  }

  .table-cell {
    height: 60px;
    line-height: 40px;
    border-right: 1px solid #DCDFE6;
    border-bottom: 1px solid #DCDFE6;
    padding: 10px;
    font-size: 14px;
    color: #606266;
    text-align: center;
    overflow: hidden;
  }

  .table-cell-title {
    border-right: 1px solid #DCDFE6;
    border-bottom: 1px solid #DCDFE6;
    padding: 10px;
    background: #F2F6FC;
    text-align: center;
    font-size: 14px;
    color: #303133;
  }
</style>
