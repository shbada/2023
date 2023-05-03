package com.itvillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * R2DBC 사용
 * - JPA처럼 엔티티에 정의된 매핑 정보로 테이블을 자동 생성해주는 기능이 없기 때문에,
 *   테이블 스크립트를 직접 작성해야한다. (/db/h2/**.sql)
 */
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@SpringBootApplication
public class Chapter18Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter18Application.class, args);
    }

}
