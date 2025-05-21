package org.esther.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * パスワードエンコーダ（MD5ハッシュ化）
 * Spring SecurityのPasswordEncoderを実装し、パスワードのエンコード・検証を行う。
 *
 * @author Esther
 * @since 2025/02/26
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {

    /**
     * パスワードをMD5でハッシュ化
     * @param rawPassword 平文パスワード
     * @return MD5ハッシュ値
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
    }

    /**
     * 入力パスワードと保存済みハッシュ値の一致確認
     * @param rawPassword 平文パスワード
     * @param encodedPassword ハッシュ済みパスワード
     * @return 一致する場合はtrue
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
    }
}
