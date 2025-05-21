package org.esther.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.esther.bean.Role;

import java.util.List;


/**
 * 役割管理マッパーインターフェース
 * ユーザーへの役割割当・取得用
 *
 * @author Esther
 * @since 2025/02/26
 */
@Mapper
public interface RolesMapper {

    /**
     * ユーザーに役割を追加する
     * @param roles 役割ID配列
     * @param uid ユーザーID
     * @return 追加件数
     */
    int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

    /**
     * ユーザーIDで役割リストを取得
     * @param uid ユーザーID
     * @return 役割リスト
     */
    List<Role> getRolesByUid(Long uid);
}
