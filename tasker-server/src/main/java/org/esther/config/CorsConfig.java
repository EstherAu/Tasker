package org.esther.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS（クロスオリジンリソースシェアリング）設定クラス
 * フロントエンドとバックエンド間の通信を許可する
 *
 * @author Esther
 * @since 2025/04/26
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // すべてのAPIエンドポイントを許可
                .allowedOriginPatterns("*")     // ★開発環境のみ推奨。本番環境ではフロントのドメイン例：「https://yourdomain.com」を指定してください。
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")      // 許可するHTTPメソッド（支持的请求方式）
                .allowedHeaders("*")        // すべてのリクエストヘッダーを許可（允许所有请求头）
                .allowCredentials(true)     // Cookie情報の送信を許可
                .maxAge(3600);              // プリフライトリクエストの有効期間（秒）（预检请求的缓存时间（秒））
    }
}