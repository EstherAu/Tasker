/*
 Date: 21/05/2025 17:02:51
*/

CREATE DATABASE tasker DEFAULT CHARACTER SET utf8mb4;

USE `tasker`;

SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
INSERT INTO `roles` (`id`, `name`) VALUES (1, '管理者');
INSERT INTO `roles` (`id`, `name`) VALUES (2, 'ユーザー');
INSERT INTO `roles` (`id`, `name`) VALUES (3, 'テスト1');
INSERT INTO `roles` (`id`, `name`) VALUES (4, 'テスト2');
INSERT INTO `roles` (`id`, `name`) VALUES (5, 'テスト3');
COMMIT;

-- ----------------------------
-- Table structure for roles_user
-- ----------------------------
DROP TABLE IF EXISTS `roles_user`;
CREATE TABLE `roles_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` int DEFAULT '2',
  `uid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  KEY `roles_user_ibfk_2` (`uid`),
  CONSTRAINT `roles_user_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `roles` (`id`),
  CONSTRAINT `roles_user_ibfk_2` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of roles_user
-- ----------------------------
BEGIN;
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (8, 2, 7);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (9, 1, 7);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (17, 5, 7);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (106, 2, 14);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (108, 2, 16);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (109, 2, 17);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (110, 2, 18);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (111, 2, 19);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (112, 2, 20);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (119, 2, 15);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (120, 5, 15);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (121, 2, 6);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (123, 2, 13);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (124, 3, 13);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (128, 2, 10);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (129, 5, 10);
INSERT INTO `roles_user` (`id`, `rid`, `uid`) VALUES (130, 1, 6);
COMMIT;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'タスク主キー',
  `task_code` varchar(30) NOT NULL COMMENT 'タスクコード（例：TASK-202504-001）',
  `task_name` varchar(255) NOT NULL COMMENT 'タスク名',
  `description` text COMMENT 'タスク概要',
  `detailed_description` text COMMENT '詳細説明',
  `category_id` int DEFAULT NULL COMMENT 'カテゴリID',
  `assignee_id` int DEFAULT NULL COMMENT '担当者ID',
  `creator_id` int DEFAULT NULL COMMENT '作成者ID',
  `start_date` datetime DEFAULT NULL COMMENT '開始日時',
  `updated_at` datetime DEFAULT NULL COMMENT '最終更新日時',
  `status_id` int DEFAULT NULL COMMENT 'ステータスID',
  `deadline` datetime DEFAULT NULL COMMENT '期限日時',
  `priority_id` int DEFAULT NULL COMMENT '優先度ID',
  `progress` int DEFAULT '0' COMMENT '進捗率（0-100）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_task_code` (`task_code`),
  KEY `category_id` (`category_id`),
  KEY `assignee_id` (`assignee_id`),
  KEY `creator_id` (`creator_id`),
  KEY `status_id` (`status_id`),
  KEY `priority_id` (`priority_id`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `task_category` (`id`),
  CONSTRAINT `task_ibfk_2` FOREIGN KEY (`assignee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `task_ibfk_3` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `task_ibfk_4` FOREIGN KEY (`status_id`) REFERENCES `task_status` (`id`),
  CONSTRAINT `task_ibfk_5` FOREIGN KEY (`priority_id`) REFERENCES `task_priority` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of task
-- ----------------------------
BEGIN;
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (12, 'TASK-202504-001', 'タスク新規作成機能の実装', '新しいタスクを登録できる画面とAPIを作成', 'PostTask.vueでフォーム入力、API経由でデータベースに新規タスクを登録できる機能を開発。バリデーションも追加。', NULL, 10, NULL, '2025-04-03 15:00:00', '2025-04-06 17:12:06', 3, '2025-04-08 15:00:00', 3, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (18, 'TASK-202504-002', 'タスク編集機能の開発', '既存タスクの内容を編集できるUIとAPIの実装', '編集ボタンからPostTask.vueへの遷移、既存データの取得、編集後の保存処理のAPI連携、エラーハンドリングも強化。', NULL, 10, NULL, '2025-04-07 15:00:00', '2025-04-10 19:04:38', 3, '2025-04-10 15:00:00', 3, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (19, 'TASK-202504-003', 'タスク削除・復元機能の実装', '単一·複数タスクの論理削除/復元の機能追加', 'TaskTable.vueで選択されたタスクの削除·復元操作が可能に。管理者専用の機能として権限制御も対応。', NULL, 10, NULL, '2025-04-10 15:00:00', '2025-04-12 18:12:18', 3, '2025-04-12 15:00:00', 3, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (20, 'TASK-202505-004', 'タスク詳細画面の作成', 'タスクの詳細情報を閲覧できる画面を作成', 'TaskDetail.vueでタスクの進捗、担当者、説明、開始日·締切日など全ての情報を一覧表示できるように実装。', NULL, 10, NULL, '2025-05-01 15:00:00', '2025-05-04 19:53:30', 3, '2025-05-04 15:00:00', 2, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (21, 'TASK-202505-005', 'タスク進捗更新機能の追加', 'タスク進捗バーの操作で進捗率を更新できるようにする。', '進捗が変更された際に状態も自動更新するロジックを実装し、UIを日文で統一する。', NULL, 10, NULL, '2025-05-03 15:00:00', '2025-05-06 15:38:57', 3, '2025-05-08 15:00:00', 2, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (22, 'TASK-202505-006', '権限制御機能の開発', '管理者・ユーザーの操作権限を適切に制御', '管理者のみタスク編集·削除·作成が可能、ユーザーは自分担当タスクの進捗更新のみ許可するロジックをAPI·UI両面で実装。', NULL, 10, NULL, '2025-05-04 15:00:00', '2025-05-05 18:12:05', 3, '2025-05-05 15:00:00', 3, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (23, 'TASK-202505-007', 'ユーザー管理機能の実装', '管理者がユーザーの有効化·無効化、ロール変更、削除をできるようにする。', 'ユーザー検索·編集·ロール編集機能の画面とAPIの連携を最適化。', NULL, 10, NULL, '2025-05-03 15:00:00', '2025-05-05 17:20:16', 3, '2025-05-06 15:00:00', 2, 100);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (24, 'TASK-202505-008', '最終統合テスト·UI調整·デプロイ準備', 'システム全体の最終テスト、UIの微調整、前後連携の確認、デプロイ直前の準備作業を実施', '全ての機能が要件通り動作するかテストし、UI/UXの細部調整·日本語表記の最終確認·API/DB連携を総点検。本番反映前のデプロイ準備·README仕上げも含め、実践的な開発フローを完了。', NULL, 10, NULL, '2025-05-10 15:00:00', '2025-05-19 20:32:15', 2, '2025-05-15 15:00:00', 3, 80);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (26, 'TASK-202505-009', 'タスク通知機能のバックエンド実装', 'タスク状態変更時のメール／システム通知自動配信機能の実装。', '担当者や管理者へ、タスク追加·進捗·締切変更等を自動で通知する機能。設計上の仕様調整および外部サービス連携部分で遅延。', NULL, 10, NULL, '2025-05-03 15:00:00', '2025-05-04 18:12:12', 4, '2025-05-08 15:00:00', 2, 30);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (27, 'TASK-202505-010', '複数担当者タスクアサイン機能の拡張', '1タスクに対し複数ユーザーを割り当てられる機能の追加·UI/DB拡張', '現在の単一担当者仕様を拡張し、タスクごとに複数担当者を柔軟にアサイン·進捗管理できるように設計変更。DBリレーションや権限分岐の見直しを検討中。', NULL, 10, NULL, '2025-05-05 00:00:00', '2025-05-06 03:05:59', 4, '2025-05-09 03:00:00', 3, 15);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (28, 'TASK-202505-011', 'APIのJSON形式対応', '全APIのリクエスト·レスポンスをJSON形式に統一する。', 'form形式からJSON形式への移行を完了し、前後連携が安定稼働するようにする。', NULL, 10, NULL, '2025-05-05 09:00:00', '2025-05-20 20:43:21', 2, '2025-05-20 09:30:00', 3, 50);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (31, 'TASK-202506-012', 'タスク番号自動生成機能の実装', '新規タスク作成時に一意なタスクコードを自動生成するロジックを追加', 'TASK-YYYYMM-連番形式などで、日付や通し番号を組み合わせたタスクコードをサーバーサイドで自動発行し、タスク管理の一意性·利便性を向上させる。既存のPostTask.vueやAPIとの連携も調整予定。', NULL, 10, NULL, '2025-05-25 10:30:50', '2025-05-20 01:10:38', 4, '2025-05-30 18:30:00', 1, 0);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (32, 'TASK-202506-013', '工数管理モジュールの新規開発', 'タスク別の作業工数を記録·集計できる新モジュールの設計·実装', '作業開始·終了時刻、実績工数の記録、ユーザー別·タスク別のレポート機能を追加し、業務工数の可視化·分析を可能にする。', NULL, 10, NULL, '2025-06-02 09:20:12', NULL, 1, '2025-06-30 18:00:00', 3, 0);
INSERT INTO `task` (`id`, `task_code`, `task_name`, `description`, `detailed_description`, `category_id`, `assignee_id`, `creator_id`, `start_date`, `updated_at`, `status_id`, `deadline`, `priority_id`, `progress`) VALUES (35, 'TASK-202506-014', 'AWSクラウド環境へのデプロイ', 'AWS（EC2＋RDS）上でのシステム本番運用化', '既存のローカル開発環境からAWSクラウドへ移行し、セキュリティ·スケーラビリティ·バックアップ体制も考慮した本番デプロイ構成を構築する。', NULL, 10, NULL, '2025-06-02 09:30:00', NULL, 1, '2025-06-30 22:00:00', 3, 0);
COMMIT;

-- ----------------------------
-- Table structure for task_assignee
-- ----------------------------
DROP TABLE IF EXISTS `task_assignee`;
CREATE TABLE `task_assignee` (
  `task_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`task_id`,`user_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `task_assignee_ibfk_1` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`),
  CONSTRAINT `task_assignee_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='タスクアサイン（多対多）';

-- ----------------------------
-- Records of task_assignee
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for task_category
-- ----------------------------
DROP TABLE IF EXISTS `task_category`;
CREATE TABLE `task_category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of task_category
-- ----------------------------
BEGIN;
INSERT INTO `task_category` (`id`, `category_name`) VALUES (4, 'テスト');
INSERT INTO `task_category` (`id`, `category_name`) VALUES (3, 'バックエンド開発');
INSERT INTO `task_category` (`id`, `category_name`) VALUES (1, 'プロダクト設計');
INSERT INTO `task_category` (`id`, `category_name`) VALUES (2, 'フロントエンド開発');
INSERT INTO `task_category` (`id`, `category_name`) VALUES (5, 'リリース・デプロイ');
COMMIT;

-- ----------------------------
-- Table structure for task_priority
-- ----------------------------
DROP TABLE IF EXISTS `task_priority`;
CREATE TABLE `task_priority` (
  `id` int NOT NULL AUTO_INCREMENT,
  `priority_name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `priority_name` (`priority_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of task_priority
-- ----------------------------
BEGIN;
INSERT INTO `task_priority` (`id`, `priority_name`) VALUES (2, '中');
INSERT INTO `task_priority` (`id`, `priority_name`) VALUES (1, '低');
INSERT INTO `task_priority` (`id`, `priority_name`) VALUES (4, '緊急');
INSERT INTO `task_priority` (`id`, `priority_name`) VALUES (3, '高');
COMMIT;

-- ----------------------------
-- Table structure for task_status
-- ----------------------------
DROP TABLE IF EXISTS `task_status`;
CREATE TABLE `task_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `status_name` (`status_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of task_status
-- ----------------------------
BEGIN;
INSERT INTO `task_status` (`id`, `status_name`) VALUES (5, 'キャンセル');
INSERT INTO `task_status` (`id`, `status_name`) VALUES (3, '完了');
INSERT INTO `task_status` (`id`, `status_name`) VALUES (4, '延期');
INSERT INTO `task_status` (`id`, `status_name`) VALUES (1, '未開始');
INSERT INTO `task_status` (`id`, `status_name`) VALUES (2, '進行中');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `email` varchar(64) DEFAULT NULL,
  `userface` varchar(255) DEFAULT NULL,
  `regTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (6, 'kanrisha', '管理者', '202cb962ac59075b964b07152d234b70', 1, 'kanrisha@tasker.com', 'https://images.pexels.com/photos/53581/bald-eagles-bald-eagle-bird-of-prey-adler-53581.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-08 09:30:22');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (7, 'admin', 'アドミン', '202cb962ac59075b964b07152d234b70', 1, 'admin@tasker.com', 'https://images.pexels.com/photos/2220336/pexels-photo-2220336.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-08 13:30:29');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (10, 'otter', 'カワウソ', '202cb962ac59075b964b07152d234b70', 1, 'otter@tasker.com', 'https://images.pexels.com/photos/28749526/pexels-photo-28749526.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (13, 'squirrel', 'リス', '202cb962ac59075b964b07152d234b70', 0, 'squirrel@tasker.com', 'https://images.pexels.com/photos/1082179/pexels-photo-1082179.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (14, 'goldfish', 'きんぎょ', '202cb962ac59075b964b07152d234b70', 0, 'goldfish@tasker.com', 'https://images.pexels.com/photos/1335971/pexels-photo-1335971.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (15, 'flamingo', 'フラミンゴ', '202cb962ac59075b964b07152d234b70', 1, 'flamingo@tasker.com', 'https://images.pexels.com/photos/9247595/pexels-photo-9247595.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (16, 'chameleon', 'カメレオン', '202cb962ac59075b964b07152d234b70', 1, 'chameleon@tasker.com', 'https://images.pexels.com/photos/62289/yemen-chameleon-chamaeleo-calyptratus-chameleon-reptile-62289.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (17, 'cat', 'ねこ', '202cb962ac59075b964b07152d234b70', 0, 'cat@tasker.com', 'https://images.pexels.com/photos/28838822/pexels-photo-28838822.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (18, 'dog', 'ワンコ', '202cb962ac59075b964b07152d234b70', 1, 'dog@tasker.com', 'https://images.pexels.com/photos/4681107/pexels-photo-4681107.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (19, 'penguin', 'ペンギン', '202cb962ac59075b964b07152d234b70', 1, 'penguin@tasker.com', 'https://images.pexels.com/photos/26045790/pexels-photo-26045790.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
INSERT INTO `user` (`id`, `username`, `nickname`, `password`, `enabled`, `email`, `userface`, `regTime`) VALUES (20, 'redpanda', 'レッサーパンダ', '202cb962ac59075b964b07152d234b70', 1, 'redpanda@tasker.com', 'https://images.pexels.com/photos/148806/pexels-photo-148806.jpeg?auto=compress&cs=tinysrgb&w=800', '2025-04-12 15:30:46');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
