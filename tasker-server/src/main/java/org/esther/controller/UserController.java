package org.esther.controller;

import org.esther.bean.RespBean;
import org.esther.service.UserService;
import org.esther.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ユーザー関連APIコントローラ
 * ログインユーザー情報取得・管理者判定など
 *
 * @author Esther
 * @since 2025/02/26
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 現在ログイン中のユーザー名（ニックネーム）を取得
     * @return ニックネーム
     */
    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return Util.getCurrentUser().getNickname();
    }

    /**
     * 現在ログイン中のユーザーIDを取得
     * @return ユーザーID
     */
    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return Util.getCurrentUser().getId();
    }

    /**
     * 現在ログイン中のメールアドレスを取得
     * @return メールアドレス
     */
    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Util.getCurrentUser().getEmail();
    }

    /**
     * 管理者権限判定API
     * @return 管理者であれば true、一般ユーザーなら false
     */
    @RequestMapping("/isAdmin")
    public Boolean isAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("管理者")) {
                return true;
            }
        }
        return false;
    }

    /**
     * ユーザーのメールアドレスを更新
     * @param email 新しいメールアドレス
     * @return 更新結果
     */
    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "メールアドレスの更新に成功しました！");
        }
        return new RespBean("error", "メールアドレスの更新に失敗しました！");
    }
}
