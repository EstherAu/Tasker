package org.esther.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.esther.bean.Role;
import org.esther.bean.User;

import java.util.List;

/**
 * ユーザー管理用Mapperインターフェース
 * ユーザー/権限管理関連のDBアクセス処理
 *
 * @author Esther
 * @since 2025/02/26
 */
@Mapper
public interface UserMapper {

    /** 全ユーザー一覧取得（管理者・一般ユーザー含む） */
    List<User> getAllUsers();

    /** ユーザー名でユーザー情報を取得（ログイン認証用） */
    User loadUserByUsername(@Param("username") String username);

    /** 新規ユーザー登録 */
    long reg(User user);

    /** ユーザーのメールアドレスを更新 */
    int updateUserEmail(@Param("email") String email, @Param("id") Long id);

    /** ニックネームでユーザー検索 */
    List<User> getUserByNickname(@Param("nickname") String nickname);

    /** 全ロール情報取得 */
    List<Role> getAllRole();

    /** ユーザー有効/無効状態の更新 */
    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

    /** ユーザーをIDで削除 */
    int deleteUserById(Long uid);

    /** 指定ユーザーの権限（ロール）全削除 */
    int deleteUserRolesByUid(Long id);

    /** ユーザーのロールを設定 */
    int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

    /** IDでユーザー情報取得 */
    User getUserById(@Param("id") Long id);
}
