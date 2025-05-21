package org.esther;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("org.esther.mapper")  // MyBatisのMapperインターフェース自動スキャン
@SpringBootApplication          // Spring Bootのアプリケーション宣言
@EnableScheduling               // スケジューリング機能を有効化
public class TaskerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskerServerApplication.class, args);
    }
}
