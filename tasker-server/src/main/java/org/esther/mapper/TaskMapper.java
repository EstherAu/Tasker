package org.esther.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.esther.bean.Task;
import java.util.List;

/**
 * タスク管理用MyBatisマッパー
 * タスクのCRUD・検索・状態更新などを担当
 *
 * @author Esther
 * @since 2025/02/26
 */
@Mapper
public interface TaskMapper {

    /**
     * タスク一覧取得
     * 状態・優先度・キーワード・担当者・ページネーションに対応
     */
    List<Task> getAllTasks(
            @Param("statusId") Integer statusId,
            @Param("priorityId") Integer priorityId,
            @Param("keywords") String keywords,
            @Param("offset") Integer offset,
            @Param("limit") Integer limit,
            @Param("assigneeId") Long assigneeId
    );

    /**
     * タスク件数取得（ページネーション用）
     */
    int getTaskCount(
            @Param("statusId") Integer statusId,
            @Param("priorityId") Integer priorityId,
            @Param("keywords") String keywords,
            @Param("assigneeId") Long assigneeId
    );

    /**
     * タスク詳細取得（ID指定）
     */
    Task getTaskById(@Param("id") Long id);

    /**
     * タスク新規作成
     */
    int createTask(Task task);

    /**
     * タスク情報更新
     */
    int updateTask(Task task);

    /**
     * 単一タスク状態・進捗更新
     */
    int updateTaskStateById(@Param("taskId") Integer taskId,
                            @Param("statusId") Integer statusId,
                            @Param("progress") Integer progress);

    /**
     * 複数タスクの状態・進捗を一括更新
     */
    int updateTaskState(@Param("taskIds") List<Integer> taskIds,
                        @Param("statusId") Integer statusId,
                        @Param("progress") Integer progress);

    /**
     * タスク一括削除（論理削除も対応可）
     */
    int deleteTaskByIds(@Param("taskIds") List<Integer> taskIds);

}
