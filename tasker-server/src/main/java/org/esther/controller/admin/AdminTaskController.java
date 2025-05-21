package org.esther.controller.admin;

import org.esther.bean.RespBean;
import org.esther.bean.Task;
import org.esther.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 管理者専用タスク管理コントローラー
 * 管理者のみが利用可能なタスク一覧・状態変更API
 *
 * @author Esther
 * @since 2025/02/26
 */
@RestController
@RequestMapping("/admin/tasks")
public class AdminTaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 管理者：全タスク一覧取得API（ステータス無視・ページング対応可）
     * @param page ページ番号（デフォルト1）
     * @param pageSize 1ページあたりの件数（デフォルト6）
     * @param keywords 検索キーワード（任意）
     * @return タスクリスト＋総件数
     */
    @GetMapping
    public Map<String, Object> getAllTasksByAdmin(@RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "6") Integer pageSize,
                                                  @RequestParam(required = false) String keywords) {
        List<Task> tasks = taskService.getAllTasks(null, null, keywords, page, pageSize);
        int totalCount = taskService.getTaskCount(null, null, keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("tasks", tasks);
        map.put("totalCount", totalCount);
        return map;
    }

    /**
     * 管理者：タスクの一括ステータス更新API（物理削除・一括復元等）
     * @param body リクエストボディ（taskIds: タスクIDリスト, statusId: 更新先ステータス, progress: 進捗％）
     * @return 更新結果（成功/失敗）
     */
    @PutMapping("/status")
    public RespBean updateTaskStatusByAdmin(@RequestBody Map<String, Object> body) {
        List<Integer> taskIds = (List<Integer>) body.get("taskIds");
        Integer statusId = (Integer) body.get("statusId");
        Integer progress = (Integer) body.get("progress");

        // ステータスIDが99の場合、物理削除処理
        if (statusId == 99) {
            int result = taskService.deleteTaskByIds(taskIds);
            return result > 0 ? new RespBean("success", "タスクを完全に削除しました")
                    : new RespBean("error", "削除に失敗しました");
        }

        int result = taskService.updateTaskState(taskIds, statusId, progress);
        return result > 0 ? new RespBean("success", "タスクの状態を更新しました")
                : new RespBean("error", "状態更新に失敗しました");
    }
}