<template>
  <el-card class="task-detail-card">
    <div slot="header" class="clearfix">
      <span style="font-size: 20px; font-weight: bold">{{ task.taskName }}</span>
      <el-tag type="info">番号：{{ task.taskCode }}</el-tag>
    </div>

    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="12">
        <el-tag :type="statusTagType(task.statusId)">状態：{{ task.statusName }}</el-tag>
      </el-col>
      <el-col :span="12">
        <el-tag :type="progressTagType(task.progress)">進捗：{{ task.progress }}%</el-tag>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-bottom: 20px">
      <el-col :span="12">
        <el-tag type="warning">優先度：{{ task.priorityName }}</el-tag>
      </el-col>
      <el-col :span="12">
        <el-tag type="success">担当者：{{ task.nickname }}</el-tag>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-bottom: 10px">
      <el-col :span="12">
        <el-tag type="">開始日時：{{ formatDate(task.startDate) }}</el-tag>
      </el-col>
      <el-col :span="12">
        <el-tag type="">締切日時：{{ formatDate(task.deadline) }}</el-tag>
      </el-col>
    </el-row>

    <el-divider></el-divider>

    <div style="margin-bottom: 20px">
      <h3>概要</h3>
      <p>{{ task.description }}</p>
    </div>

    <div>
      <h3>詳細説明</h3>
      <div v-html="task.detailedDescription"></div>
    </div>
  </el-card>
</template>

<script>
import { jsonGetRequest } from '@/utils/api';
import dayjs from 'dayjs';

export default {
  data() {
    return {
      task: {}  // 現在表示中のタスク詳細データ
    };
  },
  mounted() {
    const taskId = this.$route.query.id;
    if (taskId) {
      this.loadTask(taskId);
    } else {
      this.$message.error('タスクIDが指定されていません');
    }
  },
  methods: {
    // タスク詳細を取得
    loadTask(id) {
      jsonGetRequest(`/tasks/${id}`).then(resp => {
        if (resp.data && resp.data.status === 'success') {
          this.task = resp.data.data;
        } else {
          this.$message.error(resp.data.msg || 'タスクの取得に失敗しました');
        }
      });
    },
    // 進捗のタグ色判定
    progressTagType(progress) {
      if (progress === 100) return 'success';
      if (progress >= 60) return 'warning';
      return 'info';
    },
    // 日付フォーマット
    formatDate(dateStr) {
      return dateStr ? dayjs(dateStr).format('YYYY-MM-DD') : '-';
    },
    // ステータスのタグ色判定
    statusTagType(statusId) {
      switch (statusId) {
        case 1: return 'info';      // 未開始
        case 2: return 'primary';   // 進行中
        case 3: return 'success';   // 完了
        case 4: return 'warning';   // 延期
        case 5: return 'danger';    // キャンセル
        default: return '';
      }
    }
  }
};
</script>

<style scoped>
.task-detail-card {
  max-width: 900px;
  margin: 20px auto;
  padding: 20px;
  background: #fff;
}
p {
  line-height: 1.6;
}
</style>