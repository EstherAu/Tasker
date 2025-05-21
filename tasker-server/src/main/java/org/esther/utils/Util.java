package org.esther.utils;

import org.esther.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * ログイン中ユーザー情報取得用ユーティリティ
 * Spring Securityのコンテキストから現在のユーザーを取得
 *
 * @author Esther
 * @since 2025/02/26
 */
public class Util {
    /**
     * 現在ログイン中のユーザー情報を取得する
     * @return User 現在のユーザーオブジェクト
     */
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
