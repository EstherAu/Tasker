<template>
  <div>
    <!-- 検索バー -->
    <div style="display: flex; justify-content: flex-start; margin-bottom: 10px">
      <el-input
        placeholder="タスク名で検索..."
        prefix-icon="el-icon-search"
        v-model="keywords"
        style="width: 300px"
        size="mini"
      ></el-input>
      <el-button
        type="primary"
        icon="el-icon-search"
        size="mini"
        style="margin-left: 5px"
        @click="searchClick"
      >検索</el-button>
    </div>

    <!-- タスク一覧テーブル -->
    <el-table
      ref="taskTable"
      :data="tasks"
      :row-key="task => task.id"
      tooltip-effect="dark"
      style="width: 100%"
      max-height="500"
      @selection-change="handleSelectionChange"
      v-loading="loading"
    >
      <!-- 複数選択列（削除権限がある場合のみ選択可） -->
      <el-table-column type="selection" width="35" :selectable="row => showDelete" :reserve-selection="true" />
      <el-table-column label="タスク番号" prop="taskCode" width="160" >
        <template slot-scope="scope">
          <span style="color: #409eff; cursor: pointer" @click="goToDetail(scope.row)">
            {{ scope.row.taskCode }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="taskName" label="タスク名" />
      <el-table-column prop="statusName" label="状態" width="100" />
      <el-table-column prop="priorityName" label="優先度" width="100" />
      <el-table-column prop="progress" label="進捗" width="120" >
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.progress || 0" :show-text="true" :stroke-width="10" />
        </template>
      </el-table-column>
      <el-table-column prop="startDate" label="開始日時" width="120"  >
        <template slot-scope="scope">
          {{ formatDate(scope.row.startDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="deadline" label="期限" width="120" >
        <template slot-scope="scope">
          {{ formatDate(scope.row.deadline) }}
        </template>
      </el-table-column>
       <!-- 管理者のみ表示：担当者（実行者） -->
      <el-table-column prop="nickname" label="担当者" width="120" v-if="isAdmin">
        <template slot-scope="scope">
          <span>{{ scope.row.nickname || '-' }}</span>
        </template>
      </el-table-column>

      <!-- 操作列 -->
      <el-table-column label="操作" align="left" v-if="showEdit || showDelete || showRestore || showUpdate">
        <template slot-scope="scope">
          <!-- 編集ボタン（管理者）-->
          <el-button size="mini" v-if="showEdit" @click="editTask(scope.row)">編集</el-button>
          <!-- 復元ボタン（管理者） -->
          <el-button size="mini" v-if="showRestore" @click="restoreTask(scope.row.id)">復元</el-button>
          <!-- 削除ボタン（管理者） -->
          <el-button size="mini" type="danger" v-if="showDelete" @click="deleteTask(scope.row.id, scope.row.statusId)">削除</el-button>
          <!-- 進捗更新ボタン（担当者） -->
          <el-button size="mini" type="primary" v-if="showUpdate" @click="openUpdateDialog(scope.row)">更新</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- テーブル下部：一括削除・ページネーション -->
    <div style="display: flex; justify-content: space-between; padding-top: 10px">
      <el-button
        type="danger"
        size="mini"
        :disabled="selectedTasks.length === 0"
        v-if="showDelete"
        @click="deleteMany"
      >一括削除</el-button>

      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalCount"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="currentChange"
        v-show="tasks.length > 0"
      ></el-pagination>
    </div>

    <!-- 進捗更新ダイアログ -->
    <el-dialog title="タスク進捗の更新" :visible.sync="updateDialogVisible" width="400px">
      <el-form label-width="80px">
        <el-form-item label="状態">
          <el-select v-model="updateTask.statusId" placeholder="ステータスを選択してください">
            <el-option label="未開始" :value="1" />
            <el-option label="進行中" :value="2" />
            <el-option label="完了" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="進捗（％）">
         <el-input-number v-model="updateTask.progress" :min="0" :max="100" :step="5" />
        </el-form-item>
      </el-form>
     <div slot="footer" class="dialog-footer">
       <el-button @click="updateDialogVisible = false">キャンセル</el-button>
       <el-button type="primary" @click="submitUpdate">更新</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import dayjs from 'dayjs' 
import { jsonGetRequest, jsonPutRequest } from '@/utils/api';

export default {
  props: {
    statusId: {
      type: [String, Number],
      default: '0'
    },
    priorityId: {
      type: [String, Number],
      default: ''
    },
    showEdit: {
      type: Boolean,
      default: true
    },
    showDelete: {
      type: Boolean,
      default: true
    },
    showRestore: {
      type: Boolean,
      default: false
    },
    showUpdate: {
      type: Boolean,
      default: false
    },
    activeName: {
      type: [String, Number],
      default: '0'
    },
    isAdmin: {
    type: Boolean,
    default: false
    }    
  },
  data() {
    return {
      tasks: [],            // 表示中のタスクリスト
      selectedTasks: [],
      currentPage: 1,       // 現在のページ
      pageSize: 6,         // 1ページ当たりの表示件数
      totalCount: 0,        // 総タスク数
      keywords: '',         // 検索キーワード
      loading: false,

 // 進捗更新ダイアログ関連
  updateDialogVisible: false,
    updateTask: {
      id: null,
      statusId: null,
      progress: 0
      },
      statuses: [] // ステータスのプルダウンリスト（将来拡張用）
    };
  },
  // 子コンポーネントの自動リロード（statusId 変更時に発火） 
  watch: {
    statusId: {
      immediate: true,
      handler() {
        this.loadTasks(); 
      }
    }
  },
  mounted() {
    this.loadTasks(this.currentPage);
    if (window.bus && window.bus.$on) {
      window.bus.$on('taskTableReload', this.handleTableReload);
    }
  },
  beforeDestroy() {
    if (window.bus && window.bus.$off) {
      window.bus.$off('taskTableReload', this.handleTableReload);
    }
  },  
  methods: {
    // テーブルのリロードハンドラ
    handleTableReload() {
      this.loadTasks(this.currentPage);
    },
    // タスクリストを取得
    loadTasks(page) {
      this.loading = true;
      const params = {
        page: page,
        pageSize: this.pageSize,
        keywords: this.keywords
      };
      if (this.statusId !== null && this.statusId !== undefined) {
        params.statusId = this.statusId;
      }
      if (this.priorityId !== null && this.priorityId !== undefined) {
        params.priorityId = this.priorityId;
      }
    jsonGetRequest('/tasks', params).then(resp => {
      this.loading = false;
    // 未ログイン判定
    if (
      resp.data &&
      resp.data.status === 'error' &&
      resp.data.msg &&
      resp.data.msg.indexOf('未登录') !== -1
    ) {
      this.$message.error('先にログインしてください');
      if (this.$route.path !== '/') {
        this.$router.push('/');
      }
      return; 
    }
      if (resp.data) {
        this.tasks = resp.data.data || [];
        this.totalCount = resp.data.totalCount || 0;
        }
      });
    },
    // 日付フォーマット
    formatDate(val) {
      return val ? dayjs(val).format('YYYY-MM-DD') : ''
    },
    // ページ切替
    currentChange(page) {
      this.currentPage = page;
      this.loadTasks(page);
    },
    // 検索ボタン
    searchClick() {
      this.currentPage = 1;
      this.loadTasks(this.currentPage);
    },
    // 選択変更
    handleSelectionChange(val) {
      this.selectedTasks = val;
    },
    // 詳細ページへ遷移
    goToDetail(row) {
      this.$router.push({ path: '/taskDetail', query: { id: row.id } });
    },
     // 編集ページへ遷移
    editTask(task) {
      this.$router.push({
        path: '/postTask',
        query: { 
          id: task.id,              
          from: this.activeName    // タブ名
        }
      });
    },  
    // 進捗更新ダイアログを開く
    openUpdateDialog(task) {
      this.updateTask.id = task.id;
      this.updateTask.statusId = task.statusId;
      this.updateTask.progress = task.progress || 0;
      this.updateDialogVisible = true;
    },
    // 進捗更新を送信
    submitUpdate() {
      jsonPutRequest('/tasks/status', {
        taskIds: [parseInt(this.updateTask.id)],
        statusId: this.updateTask.statusId,
        progress: this.updateTask.progress
      }).then(resp => {
        if (resp.data && resp.data.status === 'success') {
          this.$message.success('更新しました');
          this.updateDialogVisible = false;
          this.loadTasks(this.currentPage);
        } else {
          this.$message.error(resp.data && resp.data.msg || '更新失敗');
        }
      });
    },    
    // 単体削除
    deleteTask(id, currentStatus) {
      this.$confirm('このタスクを削除しますか？', '確認', {
        confirmButtonText: 'はい', cancelButtonText: 'キャンセル', type: 'warning'
      }).then(() => {
        const statusId = currentStatus === 5 ? 99 : 5;
        jsonPutRequest('/tasks/status', { taskIds: [id], statusId }).then(resp => {
          if (resp.data && resp.data.status === 'success') {
            this.$message.success(resp.data.msg);
            this.loadTasks(this.currentPage);
          } else {
            this.$message.error(resp.data && resp.data.msg || '削除失敗');
          }
        });
      }).catch(() => {
        this.$message.info('削除をキャンセルしました');
      });
    },
    // 一括削除
    deleteMany() {
      if (this.selectedTasks.length === 0) return;
      const ids = this.selectedTasks.map(task => task.id);
      const statusId = this.selectedTasks[0].statusId === 5 ? 99 : 5;
      this.$confirm('選択したタスクを一括削除しますか？', '確認', {
        confirmButtonText: 'はい', cancelButtonText: 'キャンセル', type: 'warning'
      }).then(() => {
        jsonPutRequest('/tasks/status', { taskIds: ids, statusId }).then(resp => {
          if (resp.data && resp.data.status === 'success') {
            this.$message.success(resp.data.msg);
            this.loadTasks(this.currentPage);
          } else {
            this.$message.error(resp.data && resp.data.msg || '削除失敗');
          }
        });
      }).catch(() => {
        this.$message.info('削除をキャンセルしました');
      });
    },
    // タスク復元
    restoreTask(id) {
      jsonPutRequest('/tasks/status', { taskIds: [id], statusId: 1 }).then(resp => {
        if (resp.data && resp.data.status === 'success') {
          this.$message.success(resp.data.msg);
          this.loadTasks(this.currentPage);
        } else {
          this.$message.error(resp.data && resp.data.msg || '復元失敗');
        }
      });
    }
  }
};
</script>
