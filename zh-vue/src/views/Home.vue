<template>
  <el-container>
    <el-header>
      <el-row style="height: 100%" type="flex" align="middle">
        <el-col :span="12">
          <!-- <img src="../assets/images/logo2.png" /> -->
          <span class="title">社区住户信息后台管理系统</span>
        </el-col>

        <el-col :span="12" style="text-align: right">
          <img
            src="../assets/images/photo.png"
            style="border-radius: 25px; width: 48px; height: 48px"
          />
          <span class="name">欢迎：{{ user.username }}</span>
          <el-button class="logout" @click="logout" type="warning" size="mini"
            >退出</el-button
          >
        </el-col>
      </el-row>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu
          default-active="/welcome"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose"
          router
          unique-opened
          background-color="#1f0144"
          text-color="#fff"
          active-text-color="#c4afbb"
        >
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-s-custom"></i>
              <span slot="title">住户管理</span>
            </template>
            <!-- <el-menu-item index="/welcome">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>欢迎页</span>
              </template>
            </el-menu-item> -->

            <el-menu-item index="/householder">
              <template slot="title">
                <i class="el-icon-s-custom"></i>
                <span>户主管理</span>
              </template>
            </el-menu-item>

            <el-menu-item index="/household">
              <template slot="title">
                <i class="el-icon-s-custom"></i>
                <span>住户管理</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-office-building"></i>
              <span slot="title">社区管理</span>
            </template>
            <el-menu-item index="/community">
              <template slot="title">
                <i class="el-icon-office-building"></i>
                <span slot="title">社区管理</span>
              </template>
            </el-menu-item>
            <el-menu-item index="/electric">
              <template slot="title">
                <i class="el-icon-magic-stick"></i>
                <span>住户用电管理</span>
              </template>
            </el-menu-item>

            <el-menu-item index="/water">
              <template slot="title">
                <i class="el-icon-house"></i>
                <span>住户用水管理</span>
              </template>
            </el-menu-item>
          </el-submenu>

          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span slot="title">系统菜单</span>
            </template>

            <el-menu-item index="/user">
              <template slot="title">
                <i class="el-icon-s-custom"></i>
                <span>用户管理</span>
              </template>
            </el-menu-item>

            <el-menu-item index="/role">
              <template slot="title">
                <i class="el-icon-s-check"></i>
                <span>角色管理</span>
              </template>
            </el-menu-item>

            <el-menu-item index="/dict">
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>字典管理</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      // 左侧菜单数据
      menuList: [],
      user: {},
    };
  },

  // 生命周期函数
  created() {
    this.getCurrentUser();
  },
  methods: {
    async getCurrentUser() {
      const { data: res } = await this.$http.post(
        "/user/getCurrentUser/" + this.$store.state.currentUserId
      );
      if (res.code !== 200)
        return this.$message.error("获取失败,原因：" + res.msg);
      console.log("当前用户", res.data);
      this.user = res.data;
      // this.$store.commit("setUser", res.data);
      // console.log("setuser", this.$store.state.user);
    },

    getMenusList() {},

    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    logout() {
      window.sessionStorage.clear(); // 清空
      this.$router.push("/login"); //跳转
      history.pushState(null, null, document.URL);
    },
  },
};
</script>

<style>
.el-container {
  height: 100%;
}

.el-header {
  background-color: #021231;
  color: #fff;
}

.el-header .title {
  font-size: 20px;
  font-weight: bold;
  padding-left: 15px;
}
.el-header img {
  font-size: 0;
  vertical-align: middle;
}

.el-header .name {
  vertical-align: middle;
  font-size: 15px;
  padding-left: 15px;
}

.el-header .logout {
  vertical-align: middle;
  margin-left: 15px;
}

.el-aside {
  background-color: #1f0144;
}

.el-aside .el-menu {
  border-right: none;
}
.el-main {
  background-color: #eaedf1;
  width: 100%;
  height: 100%;
}
</style>
