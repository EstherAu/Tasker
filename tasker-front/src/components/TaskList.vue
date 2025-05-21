<template>
  <el-container class="task_list">
    <el-main class="main">
      <!-- タブメニュー：タスク状態で切り替え -->
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <!-- すべてのタスク -->
        <el-tab-pane label="すべて" name="all">
          <task_table
            :status-id="null"
            :showEdit="false"
            :showDelete="false"
            :showRestore="false"
            :showUpdate="false"
            :activeName="activeName"
            :isAdmin="!!isAdmin"
            :key="activeName"
          />
        </el-tab-pane>
        <!-- 未開始タスク -->
        <el-tab-pane label="未開始" name="notStarted">
          <task_table
            :statusId="1"
            :showEdit="!!isAdmin"
            :showDelete="!!isAdmin"
            :showRestore="false"
            :showUpdate="!isAdmin"
            :activeName="activeName"
            :isAdmin="!!isAdmin"
            :key="activeName"
          />
        </el-tab-pane>
        <!-- 進行中タスク -->
        <el-tab-pane label="進行中" name="inProgress">
          <task_table
            :statusId="2"
            :showEdit="!!isAdmin"
            :showDelete="!!isAdmin"
            :showRestore="false"
            :showUpdate="!isAdmin"
            :activeName="activeName"
            :isAdmin="!!isAdmin"
            :key="activeName"
          />
        </el-tab-pane>
        <!-- 完了タスク -->
        <el-tab-pane label="完了" name="completed">
          <task_table
            :statusId="3"
            :showEdit="!!isAdmin"
            :showDelete="!!isAdmin"
            :showRestore="false"
            :showUpdate="false"
            :activeName="activeName"
            :isAdmin="!!isAdmin"
            :key="activeName"
          />
        </el-tab-pane>
        <!-- 延期タスク -->
        <el-tab-pane label="延期" name="Postponed">
          <task_table
            :statusId="4"
            :showEdit="!!isAdmin"
            :showDelete="!!isAdmin"
            :showRestore="false"
            :showUpdate="!isAdmin"
            :activeName="activeName"
            :isAdmin="!!isAdmin"
            :key="activeName"
          />
        </el-tab-pane>
        <!-- キャンセル済みタスク -->
        <el-tab-pane label="キャンセル" name="cancelled">
          <task_table
            :statusId="5"
            :showEdit="false"
            :showDelete="!!isAdmin"
            :showRestore="!!isAdmin"
            :showUpdate="false"
            :activeName="activeName"
            :isAdmin="!!isAdmin"
            :key="activeName"
          />
        </el-tab-pane>
        <!-- タスク設定 -->
        <el-tab-pane label="タスク設定" name="taskcfg">
          <task_cfg :key="activeName" />
        </el-tab-pane>
        <!-- タスク作成（管理者のみ） -->
        <el-tab-pane label="新規作成" name="taskCreate" v-if="isAdmin">
          <post_task :key="activeName" />
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import task_table from './TaskTable.vue'
import task_cfg from './TaskCfg.vue'
import post_task from './PostTask.vue'
import { getRequest } from '@/utils/api'

export default {
  name: 'TaskList',
  components: {
    task_table,
    task_cfg,
    post_task
  },
  data() {
    return {
      activeName: this.$route.query.tab ||'all', // 現在選択中のタブ
      isAdmin: false     // 管理者フラグ
    }
  },
  watch: {
    '$route.query.tab'(val) {
      if (val) this.activeName = val
    }
  },
  mounted() {
    if (this.$route.query.tab) {
      this.activeName = this.$route.query.tab
    }
    // 管理者権限を判定（API連携）
    getRequest('/isAdmin').then(resp => {
      if (resp.status === 200) {
        this.isAdmin = !!resp.data
      }
    })
  },
  methods: {
    // タスク作成画面へ遷移
    goToCreate() {
      this.$router.push('/postTask')
    },
    // タブ切り替え時のハンドラー
    handleClick(tab) {
      this.$router.replace({ path: '/taskList', query: { tab: tab.name } })
      this.activeName = tab.name
    }
  }
}
</script>

<style scoped>
.task_list .main {
  padding: 20px;
  background-color: #fff;
  min-height: 600px;
}
</style>