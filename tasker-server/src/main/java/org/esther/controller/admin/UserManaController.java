package org.esther.controller.admin;

import org.esther.bean.RespBean;
import org.esther.bean.Role;
import org.esther.bean.User;
import org.esther.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ユーザー管理コントローラー
 * 管理者向けユーザー管理機能API
 *
 * @author Esther
 * @since 2025/02/26
 */
@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserService userService;


    /**
     * ユーザーの検索（ニックネームで部分一致）
     * @param nickname 検索キーワード（ニックネーム）
     * @return ユーザー一覧
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }


    /**
     * ユーザー詳細取得（ID指定）
     * @param id ユーザーID
     * @return ユーザー情報
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * 役割一覧取得
     * @return 役割リスト
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    /**
     * ユーザー有効・無効切替
     * @param enabled 有効フラグ
     * @param uid ユーザーID
     * @return 結果レスポンス
     */
    @RequestMapping(value = "/user/enabled", method = RequestMethod.PUT)
    public RespBean updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new RespBean("success", "更新に成功しました。");
        } else {
            return new RespBean("error", "更新に失敗しました。");
        }
    }

    /**
     * ユーザー削除
     * @param uid ユーザーID
     * @return 結果レスポンス
     */
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.DELETE)
    public RespBean deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new RespBean("success", "削除に成功しました。");
        } else {
            return new RespBean("error", "削除に失敗しました。");
        }
    }

    /**
     * ユーザーの役割更新
     * @param rids 役割IDリスト
     * @param id ユーザーID
     * @return 結果レスポンス
     */
    @RequestMapping(value = "/user/role", method = RequestMethod.PUT)
    public RespBean updateUserRoles(Long[] rids, Long id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new RespBean("success", "更新に成功しました。");
        } else {
            return new RespBean("error", "更新に失敗しました。");
        }
    }
}
