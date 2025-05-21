package org.esther.controller;

import org.esther.bean.RespBean;
import org.esther.bean.User;
import org.esther.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ログイン・ユーザー登録コントローラー
 * 認証関連API
 *
 * @author Esther
 * @since 2025/02/26
 */
@RestController
public class LoginRegController {

    @Autowired
    UserService userService;

    /**
     * ログイン失敗時のレスポンス
     * @return エラー応答
     */
    @RequestMapping("/login_error")
    public RespBean loginError() {
        return new RespBean("error", "ログインに失敗しました。");
    }

    /**
     * ログイン成功時のレスポンス
     * @return 成功応答
     */
    @RequestMapping("/login_success")
    public RespBean loginSuccess() {
        return new RespBean("success", "ログインに成功しました。");
    }

    /**
     * 未ログイン時のレスポンス
     * <p>
     * 未ログインの場合、本メソッドが呼ばれる。必要に応じてJSON/HTML切り替えも可能。
     * （如果要支持表单登录，可以在这个方法中判断请求的类型，进而决定返回JSON还是HTML页面）
     * @return エラー応答
     */
    @RequestMapping("/login_page")
    public RespBean loginPage() {
        return new RespBean("error", "未ログインです。ログインしてください。");
    }

    /**
     * ユーザー新規登録
     * @param user 登録ユーザー情報
     * @return 結果レスポンス
     */
    @PostMapping("/reg")
    public RespBean reg(User user) {
        int result = userService.reg(user);
        if (result == 0) {
            //成功
            return new RespBean("success", "登録に成功しました。");
        } else if (result == 1) {
            return new RespBean("error", "ユーザー名が既に存在します。登録できませんでした。");
        } else {
            //失败
            return new RespBean("error", "登録に失敗しました。");
        }
    }
}
