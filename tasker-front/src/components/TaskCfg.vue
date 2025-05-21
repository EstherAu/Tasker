<!-- タスク通知設定画面（フロントエンドのみのデモ版）
     - UI実装済み、バックエンド未連携 -->

     <template>
      <div class="taskcfg-container">
        <el-card>
          <div slot="header">
            <span class="cfg-title">タスク通知設定</span>
          </div>
          <el-form :model="config" label-width="160px">
            <el-form-item label="通知有効">
              <el-switch v-model="config.taskNotificationEnabled"></el-switch>
            </el-form-item>
            <el-form-item label="通知先メールアドレス">
              <el-input v-model="config.notifyEmail" placeholder="メールアドレスを入力してください"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="saveSetting">設定を保存</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
    </template>
    
    <script>
    import { jsonPutRequest } from '@/utils/api';
    
    export default {
      name: 'TaskCfg',
      data() {
        return {
          config: {
            taskNotificationEnabled: false,
            notifyEmail: ''
          }
        };
      },
      mounted() {
        // 将来的にはバックエンドから設定情報を取得可能（現時点はデフォルト値のみ）
        // 例：jsonGetRequest('/task/notice/config')
      },
      methods: {
        saveSetting() {
          // 今はダミー保存（バックエンド未接続）
          // 連携時は jsonPutRequest('/task/notice/config', this.config) で対応
          this.$message.success('タスク通知設定が保存されました！');
    
          // 実際に連携する場合のサンプル（下記コメント解除）:
          // jsonPutRequest('/task/notice/config', this.config).then(resp => {
          //   if (resp.data && resp.data.status === 'success') {
          //     this.$message.success('タスク通知設定が保存されました！');
          //   } else {
          //     this.$message.error('タスク通知設定の保存に失敗しました');
          //   }
          // }).catch(() => {
          //   this.$message.error('タスク通知設定の保存に失敗しました');
          // });
        }
      }
    };
    </script>
    
    <style scoped>
    .taskcfg-container {
      padding: 20px;
      max-width: 600px;
      margin: 0 auto;
    }
    .cfg-title {
      font-size: 18px;
      font-weight: bold;
    }
    </style>