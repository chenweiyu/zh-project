<template>
  <div>
    <!-- 2.卡片视图 -->
    <el-card>
      <!-- 1.导航条 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>住户管理</el-breadcrumb-item>
        <el-breadcrumb-item>住户信息列表</el-breadcrumb-item>
      </el-breadcrumb>
      <!-- 2.1 顶部操作 -->
      <br />

      <el-row :gutter="20">
        <el-col :span="5">
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
          label="姓名"
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
          width="80px"
        ></el-table-column>
        <el-table-column
          label="是否为户主"
          prop="householdIs"
          align="center"
          width="100px"
        ></el-table-column>
        <el-table-column
          label="与户主关系"
          prop="householdRelation"
          align="center"
          width="100px"
        ></el-table-column>
        <el-table-column
          label="籍贯"
          prop="householdNativePlace"
          align="center"
        >
        </el-table-column>
        <el-table-column label="出生日期" prop="householdBirth" align="center">
        </el-table-column>
        <el-table-column label="操作" width="200px" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="showEditDialog(scope.row.id)"
              >编辑
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-message"
              @click="showDetailDialog([scope.row.id])"
              >详情
            </el-button>
            <el-button
              size="mini"
              type="text"
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
      title="添加住户信息"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
    >
      <el-form
        :model="addHouseholdForm"
        ref="addHouseholdFormRef"
        :rules="addHouseholdFormRules"
        label-width="120px"
      >
        <el-form-item label="姓名" prop="householdName">
          <el-input v-model="addHouseholdForm.householdName"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="householdPhone">
          <el-input
            v-model="addHouseholdForm.householdPhone"
            type="phone"
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="householdSex">
              <el-select
                v-model="addHouseholdForm.householdSex"
                placeholder="请选择性别"
              >
                <el-option
                  v-for="(item, index) in sexList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出身日期" prop="householdBirth">
              <el-date-picker
                v-model="addHouseholdForm.householdBirth"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否为户主" prop="householdIs">
              <el-select
                v-model="addHouseholdForm.householdIs"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="与户主关系" prop="householdRelation">
              <el-select
                v-model="addHouseholdForm.householdRelation"
                placeholder="请选择关系"
              >
                <el-option
                  v-for="item in relationList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="addHouseholdForm.householdIs == '否'">
          <el-col :span="12">
            <el-form-item label="所属户主姓名" prop="householderName">
              <el-select
                v-model="addHouseholdForm.householderId"
                placeholder="请选择户主名称"
                @change="autoCommunityName"
              >
                <el-option
                  v-for="item in holderAndCommList"
                  :key="item.id"
                  :label="item.householderName"
                  :value="item.householderId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在社区" prop="communityName">
              <el-input v-model="communityName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="籍贯" prop="householdNativePlace">
          <el-input v-model="addHouseholdForm.householdNativePlace"></el-input>
        </el-form-item>

        <el-form-item label="身份号码" prop="householdIdentify">
          <el-input v-model="addHouseholdForm.householdIdentify"></el-input>
        </el-form-item>
        <el-form-item label="户籍所在地" prop="householdHjPlace">
          <el-input v-model="addHouseholdForm.householdHjPlace"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="政治面貌" prop="householdOutlook">
              <el-select
                v-model="addHouseholdForm.householdOutlook"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in outlookList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="退役军人" prop="householdVeterans">
              <el-select
                v-model="addHouseholdForm.householdVeterans"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否军人家庭" prop="householdSoldierFam">
              <el-select
                v-model="addHouseholdForm.householdSoldierFam"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否为烈士家属" prop="householdMartyrFam">
              <el-select
                v-model="addHouseholdForm.householdMartyrFam"
                placeholder="请选择"
                style="margin-rignt: 20px"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="success" @click="add">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 编辑框 -->
    <el-dialog
      title="编辑住户信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form
        :model="editHouseholdForm"
        ref="editHouseholdFormRef"
        :rules="editHouseholdFormRules"
        label-width="120px"
      >
        <el-form-item label="姓名" prop="householdName">
          <el-input v-model="editHouseholdForm.householdName"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="householdPhone">
          <el-input
            v-model="editHouseholdForm.householdPhone"
            type="phone"
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="householdSex">
              <el-select
                v-model="editHouseholdForm.householdSex"
                placeholder="请选择性别"
              >
                <el-option
                  v-for="(item, index) in sexList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出身日期" prop="householdBirth">
              <el-date-picker
                v-model="editHouseholdForm.householdBirth"
                type="date"
                placeholder="选择日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否为户主" prop="householdIs">
              <el-select
                v-model="editHouseholdForm.householdIs"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="与户主关系" prop="householdRelation">
              <el-select
                v-model="editHouseholdForm.householdRelation"
                placeholder="请选择关系"
              >
                <el-option
                  v-for="item in relationList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="editHouseholdForm.householdIs == '否'">
          <el-col :span="12">
            <el-form-item label="所属户主姓名" prop="householderName">
              <el-select
                v-model="editHouseholdForm.householderId"
                placeholder="请选择户主名称"
                @change="autoCommunityName"
              >
                <el-option
                  v-for="item in holderAndCommList"
                  :key="item.id"
                  :label="item.householderName"
                  :value="item.householderId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在社区" prop="editCommunityName">
              <el-input v-model="editCommunityName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="籍贯" prop="householdNativePlace">
          <el-input v-model="editHouseholdForm.householdNativePlace"></el-input>
        </el-form-item>

        <el-form-item label="身份号码" prop="householdIdentify">
          <el-input v-model="editHouseholdForm.householdIdentify"></el-input>
        </el-form-item>
        <el-form-item label="户籍所在地" prop="householdHjPlace">
          <el-input v-model="editHouseholdForm.householdHjPlace"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="政治面貌" prop="householdOutlook">
              <el-select
                v-model="editHouseholdForm.householdOutlook"
                placeholder="请选择"
              >
                <el-option
                  v-for="item in outlookList"
                  :key="item.id"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="退役军人" prop="householdVeterans">
              <el-select
                v-model="editHouseholdForm.householdVeterans"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否军人家庭" prop="householdSoldierFam">
              <el-select
                v-model="editHouseholdForm.householdSoldierFam"
                placeholder="请选择"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否为烈士家属" prop="householdMartyrFam">
              <el-select
                v-model="editHouseholdForm.householdMartyrFam"
                placeholder="请选择"
                style="margin-rignt: 20px"
              >
                <el-option
                  v-for="(item, index) in yesOrNoList"
                  :key="index"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!-- 底部 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 详情框 -->
    <el-dialog
      title="住户信息详情"
      :visible.sync="detailDialogVisible"
      width="50%"
    >
      <el-form
        :model="householdDetail"
        ref="editHouseholdFormRef"
        :rules="editHouseholdFormRules"
        label-width="120px"
      >
        <el-form-item label="姓名" prop="householdName">
          <el-input v-model="householdDetail.householdName"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" prop="householdPhone">
          <el-input
            v-model="householdDetail.householdPhone"
            type="phone"
          ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="householdSex">
              <el-input
                v-model="householdDetail.householdSex"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出身日期" prop="householdBirth">
              <el-input
                v-model="householdDetail.householdBirth"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否为户主" prop="householdIs">
              <el-input
                v-model="householdDetail.householdIs"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="与户主关系" prop="householdRelation">
              <el-input
                v-model="householdDetail.householdRelation"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="householdDetail.householdIs == '否'">
          <el-col :span="12">
            <el-form-item label="所属户主姓名" prop="householderName">
              <el-input
                v-model="householdDetail.householderName"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在社区" prop="editCommunityName">
              <el-input v-model="editCommunityName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="籍贯" prop="householdNativePlace">
          <el-input v-model="householdDetail.householdNativePlace"></el-input>
        </el-form-item>

        <el-form-item label="身份号码" prop="householdIdentify">
          <el-input v-model="householdDetail.householdIdentify"></el-input>
        </el-form-item>
        <el-form-item label="户籍所在地" prop="householdHjPlace">
          <el-input v-model="householdDetail.householdHjPlace"></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="政治面貌" prop="householdOutlook">
              <el-input
                v-model="householdDetail.householdOutlook"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="退役军人" prop="householdVeterans">
              <el-input
                v-model="householdDetail.householdVeterans"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否军人家庭" prop="householdSoldierFam">
              <el-input
                v-model="householdDetail.householdSoldierFam"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否为烈士家属" prop="householdMartyrFam">
              <el-input
                v-model="householdDetail.householdMartyrFam"
                type="phone"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
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

      queryHouseholder: {
        query: "",
        pagenum: 1,
        pagesize: 1000,
      },

      multipleSelection: [], //多选  id

      list: [],
      communityList: [],
      total: 0,
      householderList: [],
      holderAndCommList: [],
      communityName: "",
      editHouseholderName: "",
      editCommunityName: "",
      sexList: [],
      yesOrNoList: [],
      outlookList: [],
      relationList: [],
      addDialogVisible: false,
      householderName: "",
      addHouseholdForm: {
        householdName: "",
        householdPhone: "",
        householdSex: "",
        householdIs: "",
        householderId: "",
        householdRelation: "",
        householdNativePlace: "",
        householdBirth: "",
        householdIdentify: "",
        householdHjPlace: "",
        householdOutlook: "",
        householdVeterans: "",
        householdSoldierFam: "",
        householdMartyrFam: "",
        communityId: "",
      },

      addHouseholdFormRules: {
        householdName: [
          { required: true, message: "请输入住户姓名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        householdPhone: [
          { required: true, message: "请输入住户的手机号", trigger: "blur" },
          {
            validator: function (rule, value, callback) {
              if (
                /^(13[0-9]|14[0-9]|15[0-9]|16[6]|18[0-9]|19[6,9]|17[0-9])\d{8}$/i.test(
                  value
                ) == false
              ) {
                callback(new Error("请输入手机号"));
              } else {
                //校验通过
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        householdSex: [
          { required: true, message: "请选择住户性别", trigger: "blur" },
        ],
        householdIs: [
          { required: true, message: "请选择住户是否为户主", trigger: "blur" },
        ],
        householdName: [
          { required: true, message: "请输入选择户主姓名", trigger: "blur" },
        ],
        householdRelation: [
          { required: true, message: "请选择住户与户主关系", trigger: "blur" },
        ],
        householdNativePlace: [
          { required: true, message: "请输入住户籍贯", trigger: "blur" },
          { min: 1, max: 100, message: "长度在100个字符", trigger: "blur" },
        ],
        householdBirth: [
          { required: true, message: "请选择住户的出生日期", trigger: "blur" },
        ],
      },

      editDialogVisible: false,

      editHouseholdForm: {},
      editHouseholdFormRules: {
        householdName: [
          { required: true, message: "请输入住户姓名", trigger: "blur" },
          {
            min: 1,
            max: 20,
            message: "长度在 3 到 20 个字符",
            trigger: "blur",
          },
        ],
        householdPhone: [
          { required: true, message: "请输入住户的手机号", trigger: "blur" },
          {
            validator: function (rule, value, callback) {
              if (
                /^(13[0-9]|14[0-9]|15[0-9]|16[6]|18[0-9]|19[6,9]|17[0-9])\d{8}$/i.test(
                  value
                ) == false
              ) {
                callback(new Error("请输入手机号"));
              } else {
                //校验通过
                callback();
              }
            },
            trigger: "blur",
          },
        ],
        householdSex: [
          { required: true, message: "请选择住户性别", trigger: "blur" },
        ],
        householdIs: [
          { required: true, message: "请选择住户是否为户主", trigger: "blur" },
        ],
        householdName: [
          { required: true, message: "请输入选择户主姓名", trigger: "blur" },
        ],
        householdRelation: [
          { required: true, message: "请选择住户与户主关系", trigger: "blur" },
        ],
        householdNativePlace: [
          { required: true, message: "请输入住户籍贯", trigger: "blur" },
          { min: 1, max: 100, message: "长度在100个字符", trigger: "blur" },
        ],
        householdBirth: [
          { required: true, message: "请选择住户的出生日期", trigger: "blur" },
        ],
      },

      detailDialogVisible: false,
      householdDetail: {},
    };
  },
  //生命函数
  created() {
    this.getList();
    this.getSexList();
    this.getYesOrNoList();
    this.getOutlootList();
    this.getRelationList();
  },
  //方法
  methods: {
    async getSexList() {
      const { data: res } = await this.$http.get("/dict/showdict/sex");
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      for (var i = 0; i < res.data.length; i++) {
        this.sexList[i] = res.data[i].code;
      }
    },

    async getYesOrNoList() {
      const { data: res } = await this.$http.get("/dict/showdict/yesOrNo");
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      console.log("res.data", res.data);
      for (var i = 0; i < res.data.length; i++) {
        this.yesOrNoList[i] = res.data[i].code;
      }
    },

    async getOutlootList() {
      const { data: res } = await this.$http.get("/dict/showdict/outlook");
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      console.log("res.data", res.data);
      for (var i = 0; i < res.data.length; i++) {
        this.outlookList[i] = res.data[i].code;
      }
    },

    async getRelationList() {
      const { data: res } = await this.$http.get("/dict/showdict/andHouseholderRation");
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      console.log("res.data", res.data);
      for (var i = 0; i < res.data.length; i++) {
        this.relationList[i] = res.data[i].code;
      }
    },

    autoCommunityName(householderId) {
      for (var i = 0; i < this.holderAndCommList.length; i++) {
        if (householderId == this.holderAndCommList[i].householderId) {
          this.addHouseholdForm.communityId =
            this.holderAndCommList[i].communityId;
          this.communityName = this.holderAndCommList[i].communityName;
          this.editCommunityName = this.holderAndCommList[i].communityName;
          break;
        }
      }
    },

    async getAllHouseholderName() {
      const { data: res } = await this.$http.post("/householder/list");
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！");
      }
      this.householderList = res.data;

      const { data: res1 } = await this.$http.post("/community/list");
      if (res1.code !== 200) {
        return this.$message.error("获取列表失败！");
      }
      this.communityList = res1.data;
      var k = 0;
      for (var i = 0; i < this.householderList.length; i++) {
        for (var j = 0; j < this.communityList.length; j++) {
          if (this.householderList[i].communityId == this.communityList[j].id) {
            this.holderAndCommList[k] = {
              householderId: this.householderList[i].id,
              householderName: this.householderList[i].householderName,
              communityId: this.communityList[j].id,
              communityName: this.communityList[j].communityName,
            };
            k++;
            break;
          }
        }
      }
    },

    //查询  async  await
    async getList() {
      const { data: res } = await this.$http.post(
        "/household/list",
        this.$qs.stringify(this.queryInfo)
      );
      if (res.code !== 200) {
        return this.$message.error("获取列表失败！,错误信息为：" + res.msg);
      }
      this.list = res.data.records;
      this.total = res.data.total;
    },

    //添加
    showAddDialog() {
      this.addDialogVisible = true;
      this.getAllHouseholderName();
    },

    add() {
      this.$refs.addHouseholdFormRef.validate(async (valid) => {
        if (!valid) {
          return;
        }
        const { data: res } = await this.$http.post(
          "/household/save",
          this.addHouseholdForm
        );
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
      this.$refs.addHouseholdFormRef.resetFields(); //清空表单
      this.communityName = "";
    },

    //编辑
    async showEditDialog(id) {
      this.getAllHouseholderName();
      const { data: res } = await this.$http.get("/household/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.editHouseholdForm = res.data;
      for (var i = 0; i < this.holderAndCommList.length; i++) {
        if (
          this.editHouseholdForm.householderId ==
          this.holderAndCommList[i].householderId
        ) {
          this.editHouseholderName = this.holderAndCommList[i].householderName;
          this.editCommunityName = this.holderAndCommList[i].communityName;
          break;
        }
      }
      this.editDialogVisible = true;
    },

    async showDetailDialog(id) {
      const { data: res } = await this.$http.get("/household/info/" + id);
      if (res.code !== 200) {
        return this.$message.error("查询信息失败！");
      }
      this.householdDetail = res.data;
      this.detailDialogVisible = true;
    },

    async update() {
      this.$refs.editHouseholdFormRef.validate(async (valid) => {
        //失败跳出
        if (!valid) return;
        const { data: res } = await this.$http.post(
          "/household/update",
          this.editHouseholdForm
        );
        if (res.code !== 200) {
          return this.$message.error("更新信息失败！错误信息为：" + res.msg);
        }
        // 提示修改成功
        this.$message.success("更新信息成功！");
        // 关闭对话框
        this.editDialogVisible = false;
        // 刷新数据列表
        this.getList();
      });
    },
    editDialogClosed() {
      this.$refs.editHouseholdFormRef.resetFields();
    },

    removeById(ids) {
      if (ids.length == 0) return this.$message.error("请先选择至少一条");
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const { data: res } = await this.$http.post("/household/delete", ids);
          if (res.code !== 200) {
            return this.$message.error("删除信息失败！");
          }
          this.$message({
            type: "success",
            message: "删除信息成功!",
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
