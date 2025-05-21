package org.esther.controller;

import org.esther.bean.RespBean;
import org.esther.bean.Task;
import org.esther.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * タスク管理コントローラー
 * タスクのCRUDおよび状態操作API
 *
 * @author Esther
 * @since 2025/02/26
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;


    /**
     * タスク新規作成
     * @param task タスク情報
     * @return 作成結果（ID含む）
     */
    @PostMapping
    public RespBean createTask(@RequestBody Task task) {
        if (task.getTaskName() == null || task.getTaskName().trim().isEmpty()) {
            return new RespBean("error", "タスク名は必須です。");        // 入力チェック（必須）
        }

        Task created = taskService.createTask(task);
        return (created != null && created.getId() != null)
                ? new RespBean("success", "タスク作成に成功しました。", created)
                : new RespBean("error", "タスク作成に失敗しました。", null);
    }

    /**
     * タスクIDで詳細取得
     * @param id タスクID
     * @return タスク詳細
     */
    @GetMapping("/{id}")
    public RespBean getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return task != null ? new RespBean("success", "タスク詳細を取得しました。", task)
                : new RespBean("error", "該当タスクが存在しません。");
    }

    /**
     * タスク総数取得（ページネーション用）
     * @param statusId 状態ID
     * @param priorityId 優先度ID
     * @param keywords キーワード
     * @return 総件数
     */
    @GetMapping("/count")
    public RespBean getTaskCount(@RequestParam(required = false) Integer statusId,
                                 @RequestParam(required = false) Integer priorityId,
                                 @RequestParam(required = false) String keywords) {
        int count = taskService.getTaskCount(statusId, priorityId, keywords);
        return new RespBean("success", "タスク総数を取得しました。", count);
    }

    /**
     * タスクリスト取得（状態・優先度・キーワード・ページ対応）
     * @param statusId 状態ID
     * @param priorityId 優先度ID
     * @param keywords キーワード
     * @param page ページ番号
     * @param pageSize 1ページの件数
     * @return タスク一覧 + 総数
     */
    @GetMapping
    public Map<String, Object> getAllTasks(
            @RequestParam(required = false) Integer statusId,
            @RequestParam(required = false) Integer priorityId,
            @RequestParam(required = false) String keywords,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "6") Integer pageSize){
        List<Task> tasks = taskService.getAllTasks(statusId, priorityId, keywords, page, pageSize);
        int totalCount = taskService.getTaskCount(statusId, priorityId, keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("data", tasks);
        map.put("totalCount", totalCount);
        return map;
    }

    /**
     * タスク内容更新
     * @param task 更新タスク
     * @return 更新結果
     */
    @PutMapping
    public RespBean updateTask(@RequestBody Task task) {
        int result = taskService.updateTask(task);
        return result > 0 ? new RespBean("success","タスク更新に成功しました。")
                : new RespBean("error","タスク更新に失敗しました。");
    }

    /**
     * タスク状態更新（単体・一括・物理削除）
     * @param req タスクID・状態ID・進捗
     * @return 更新/削除結果
     */
    @PutMapping("/status")
    public RespBean updateTaskStatus(@RequestBody Map<String, Object> req) {
        List<Integer> taskIds = (List<Integer>) req.get("taskIds");
        Integer statusId = (Integer) req.get("statusId");
        Integer progress = (Integer) req.get("progress");

        if (taskIds == null || taskIds.isEmpty() || statusId == null) {
            return new RespBean("error", "パラメータ(taskIds/statusId)は必須です。");
        }

        if (statusId == 99) {
            int deleted = taskService.deleteTaskByIds(taskIds);
            return deleted > 0 ? new RespBean("success", "タスクを完全に削除しました。")
                    : new RespBean("error", "削除に失敗しました。");
        }

        int updated = taskService.updateTaskState(taskIds, statusId, progress);
        return updated > 0 ? new RespBean("success", "タスク状態を更新しました。")
                : new RespBean("error", "状態更新に失敗しました。");
    }
}
