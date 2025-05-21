package org.esther.config;

import org.esther.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Spring Securityのセキュリティ設定クラス
 * ユーザー認証と認可（権限制御）を構成する。
 *
 * @author Esther
 * @since 2025/02/26
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    /**
     * ユーザー認証の設定
     * @param auth AuthenticationManagerBuilder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    /**
     * HTTPリクエストの認可・認証・セッション管理設定
     * @param http HttpSecurity
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // カテゴリー管理（/admin/category/all）はログイン必須
                .antMatchers("/admin/category/all").authenticated()
                // /admin配下と/regは「管理者」ロールのみ許可（/admin/**的URL都需有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")）
                .antMatchers("/admin/**","/reg").hasRole("管理者")
                // その他全てのリクエストは認証済みユーザーのみ許可
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login_page")
                // 認証成功時のハンドリング
                .successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                // ★必要に応じて「ログイン成功」メッセージを日本語化可能
                out.write("{\"status\":\"success\",\"msg\":\"ログイン成功\"}");
                out.flush();
                out.close();
            }
        })
                // 認証失敗時のハンドリング
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"status\":\"error\",\"msg\":\"ログイン失敗\"}");
                        out.flush();
                        out.close();
                    }
                }).loginProcessingUrl("/login")
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and().logout().permitAll().and().csrf().disable().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
    }

    /**
     * 静的リソース（画像・HTML・JS等）はセキュリティ対象外とする
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**","/index.html","/static/**");
    }

    /**
     * 権限不足時のハンドラBean
     * @return AccessDeniedHandler
     */
    @Bean
    AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }
}