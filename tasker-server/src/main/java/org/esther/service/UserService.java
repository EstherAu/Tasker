package org.esther.service;

import org.esther.bean.Role;
import org.esther.bean.User;
import org.esther.mapper.RolesMapper;
import org.esther.mapper.UserMapper;
import org.esther.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ユーザー管理サービスクラス
 * ログイン・ユーザー管理・権限付与などのビジネスロジックを担当
 *
 * @author Esther
 * @since 2025/02/26
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * すべての一般ユーザーを取得
     * @return ユーザーリスト
     */
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    /**
     * Spring Security用ユーザー認証（UserDetailsService実装）
     * @param s ユーザー名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            // null回避のため空ユーザーを返却（認証は失敗する仕様）
            return new User();
        }
        // ユーザーの権限情報を取得し、userにセット
        List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    /**
     * ユーザー登録処理
     * @param user ユーザー情報
     * @return 0=成功, 1=ユーザー名重複, 2=失敗
     */
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        // パスワードを暗号化し、ユーザー新規登録
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        // デフォルトで「一般ユーザー」権限を付与
        String[] roles = new String[]{"2"};
        int i = rolesMapper.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * メールアドレス更新
     */
    public int updateUserEmail(String email) {
        return userMapper.updateUserEmail(email, Util.getCurrentUser().getId());
    }

    /**
     * ニックネーム検索
     */
    public List<User> getUserByNickname(String nickname) {
        List<User> list = userMapper.getUserByNickname(nickname);
        return list;
    }

    /**
     * すべての権限情報を取得
     */
    public List<Role> getAllRole() {
        return userMapper.getAllRole();
    }

    /**
     * ユーザー有効化/無効化切替
     */
    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userMapper.updateUserEnabled(enabled, uid);
    }

    /**
     * ユーザー削除
     */
    public int deleteUserById(Long uid) {
        return userMapper.deleteUserById(uid);
    }

    /**
     * ユーザーの権限更新
     */
    public int updateUserRoles(Long[] rids, Long id) {
        int i = userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids, id);
    }

    /**
     * IDによるユーザー取得
     */
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }
}
