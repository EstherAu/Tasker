package org.esther.controller;

import org.esther.bean.RespBean;
import org.esther.bean.User;
import org.esther.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ユーザー情報取得APIコントローラ
 * タスク作成画面などで担当者リストを取得
 *
 * @author Esther
 * @since 2025/02/26
 */
@RestController
@RequestMapping("/user-api")
public class UserApiController {
    @Autowired
    private UserService userService;

    /**
     * 全ユーザー一覧取得API
     * @return ユーザー一覧・取得結果
     */
    @GetMapping("/all")
    public RespBean getAllUsers() {
        List<User> list = userService.getAllUsers();
        return new RespBean("success", "ユーザー一覧の取得に成功しました", list);
    }
}
