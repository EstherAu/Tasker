<template>
  <el-container>
    <el-main>
      <h2>{{ task.id ? 'タスク編集' : '新規タスク作成' }}</h2>
      <el-form :model="task" label-width="100px" style="max-width: 600px">
        <el-form-item label="タスクコード">
          <el-input v-model="task.taskCode" />
        </el-form-item>
        <el-form-item label="タスク名">
          <el-input v-model="task.taskName" />
        </el-form-item>
        <el-form-item label="ステータス">
          <el-select v-model="task.statusId" placeholder="選択してください">
            <el-option label="未開始" :value="1" />
            <el-option label="進行中" :value="2" />
            <el-option label="完了" :value="3" />
            <el-option label="延期" :value="4" />
            <el-option label="キャンセル" :value="5" />
          </el-select>
        </el-form-item>
        <el-form-item label="優先度">
          <el-select v-model="task.priorityId" placeholder="選択してください">
            <el-option label="低" :value="1" />
            <el-option label="中" :value="2" />
            <el-option label="高" :value="3" />
            <el-option label="緊急" :value="4" />
          </el-select>
        </el-form-item>

        <el-form-item label="進捗（％）">
          <el-input-number v-model="task.progress" :min="0" :max="100" label="進捗" />
        </el-form-item>

        <el-form-item label="担当者">
          <el-select v-model="task.assigneeId" placeholder="担当者を選択">
            <el-option v-for="user in assignees" :key="user.id" :label="user.nickname" :value="user.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="開始日時">
          <el-date-picker v-model="task.startDate" type="datetime" placeholder="選択してください" />
        </el-form-item>
        <el-form-item label="締切">
          <el-date-picker v-model="task.deadline" type="datetime" placeholder="選択してください" />
        </el-form-item>
        <el-form-item label="説明">
          <el-input type="textarea" v-model="task.description" />
        </el-form-item>
        <el-form-item label="詳細説明">
          <el-input type="textarea" v-model="task.detailedDescription" />
        </el-form-item>
      </el-form>

      <div style="margin-top: 20px">
        <el-button @click="resetForm">クリア</el-button>
        <el-button type="primary" @click="submitTask">保存</el-button>
        <el-button v-if="from" type="warning" @click="cancelEdit">戻る</el-button>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import { jsonPostRequest, jsonPutRequest, jsonGetRequest } from '@/utils/api';

export default {
  data() {
    return {
      task: {
        taskCode: '',
        taskName: '',
        description: '',
        detailedDescription: '',
        statusId: null,
        priorityId: null,
        assigneeId: null,
        startDate: '',
        deadline: '',
        progress: 0 // 初期値は数値型が良い
      },
      from: null,
      assignees: [] // 担当者一覧
    };
  },
  mounted() {
    this.from = this.$route.query.from;
    const taskId = this.$route.query.id;
    if (taskId) {
      jsonGetRequest(`/tasks/${taskId}`).then(resp => {
        if (resp.data && resp.data.status === 'success') {
          this.task = resp.data.data;
        }
      });
    }
    this.fetchAssignees();
  },
  methods: {
    // 担当者リストを取得
    fetchAssignees() {
      jsonGetRequest('/user-api/all').then(resp => {
        if (resp.data && resp.data.status === 'success') {
          this.assignees = resp.data.data;
        }
      });
    },
    // タスクを保存（新規 or 更新）
    submitTask() {
      const method = this.task.id ? jsonPutRequest : jsonPostRequest;
      method('/tasks', this.task).then(resp => {
        if (resp.data && resp.data.status === 'success') {
          this.$message.success('保存しました');
          // 編集操作後、自動リロード
          if (window.bus && window.bus.$emit) {
            window.bus.$emit('taskTableReload');
          }
          this.$router.push({ path: '/taskList', query: { tab: 'all' } });
        } else {
          this.$message.error(resp.data && resp.data.msg || '保存に失敗しました');
        }
      });
    },
    // フォームをリセット
    resetForm() {
      this.task = {
        taskCode: '',
        taskName: '',
        description: '',
        detailedDescription: '',
        statusId: null,
        priorityId: null,
        assigneeId: null,
        startDate: '',
        deadline: '',
        progress: 0
      };
    },
    // 戻る
    cancelEdit() {
      this.$router.go(-1);
    }
  }
};
</script>