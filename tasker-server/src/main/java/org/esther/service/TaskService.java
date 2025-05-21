package org.esther.service;

import org.esther.bean.Task;
import org.esther.mapper.TaskMapper;
import org.esther.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * タスク管理サービス
 * タスクCRUD・権限判定など、ビジネスロジックを担当
 *
 * @author Esther
 * @since 2025/02/26
 */
@Service
public class TaskService {
    @Autowired
    private TaskMapper taskMapper;

    /**
     * タスク新規作成
     * @param task タスクエンティティ
     * @return DB登録後のタスク（ID含む）
     */
    @Transactional
    public Task createTask(Task task) {
        if (task.getTaskName() == null || task.getTaskName().trim().isEmpty()) {
            throw new IllegalArgumentException("タスク名は必須です");
        }
        taskMapper.createTask(task);
        return task;    // DB自動生成ID入り
    }

    /**
     * タスクIDでタスク取得
     * @param id タスクID
     * @return タスクエンティティ
     */
    public Task getTaskById(Long id) {
        return taskMapper.getTaskById(id);
    }

    /**
     * タスク総数取得（ページング対応）
     * 管理者は全件、一般ユーザーは自分担当タスクのみ
     */
    public int getTaskCount(Integer statusId, Integer priorityId, String keywords) {
        Long assigneeId = null;
        if (!isCurrentUserAdmin()) {
            assigneeId = Util.getCurrentUser().getId();
        }
        return taskMapper.getTaskCount(statusId, priorityId, keywords, assigneeId);
    }

    /**
     * タスク一覧取得（ページング・条件対応）
     * 管理者は全件、一般ユーザーは自分担当分のみ
     */
    public List<Task> getAllTasks(Integer statusId, Integer priorityId, String keywords, Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        int limit = pageSize;
        Long assigneeId = null;
        if (!isCurrentUserAdmin()) {
            assigneeId = Util.getCurrentUser().getId();
        }
        return taskMapper.getAllTasks(statusId, priorityId, keywords, offset, limit,assigneeId);
    }

    /**
     * 現在のユーザーが管理者か判定
     * 権限リストに「管理者」が含まれる場合true
     */
    private boolean isCurrentUserAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("管理者")) {
                return true;
            }
        }
        return false;
    }

    /**
     * タスク内容更新
     * @param task タスクエンティティ（ID必須）
     * @return 更新件数
     */
    @Transactional
    public int updateTask(Task task) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("タスクIDは必須です");
        }
        return taskMapper.updateTask(task);
    }

    /**
     * タスク状態一括更新（単件・複数件両対応）
     */
    @Transactional
    public int updateTaskState(List<Integer> taskIds, Integer statusId, Integer progress) {
        return taskMapper.updateTaskState(taskIds, statusId, progress);
    }

    /**
     * 単一タスク状態更新（ラップ用）
     */
    @Transactional
    public int updateTaskStateById(Integer taskId, Integer statusId, Integer progress) {
        return updateTaskState(Collections.singletonList(taskId), statusId, progress);
    }

    /**
     * タスク一括削除
     * @param taskIds タスクIDリスト
     * @return 削除件数
     */
    @Transactional
    public int deleteTaskByIds(List<Integer> taskIds ) {
        return taskMapper.deleteTaskByIds(taskIds);
    }
}





