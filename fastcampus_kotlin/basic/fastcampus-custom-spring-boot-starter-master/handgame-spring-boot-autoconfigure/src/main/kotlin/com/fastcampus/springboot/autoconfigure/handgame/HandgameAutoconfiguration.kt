package com.fastcampus.springboot.autoconfigure.handgame

import com.fastcampus.springboot.handgame.Handgame
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean

@AutoConfiguration
/*
@Conditional
- 어노테이션으로 조건부를 Bean을 스프링 컨테이너에 등록하는 역할

@ConditionalOnClass : 특정 Class 파일이 존재하면 Bean 을 등록
 */
@ConditionalOnClass(Handgame::class)
/*
@ConditionalOnProperty
구성 속성의 존재와 값에 따라 조건부로 일부 빈 을 생성 해야 할 경우
> 파일위치 : basic/fastcampus-custom-spring-boot-starter-master/handgame-spring-boot-app/src/main/resources/application.properties
> 내용 : my.handgame.enabled=true
- name (=value) 속성은 application.yml 의 key
- havingValue 속성은 application.yml의 key에 매핑되는 value

my.nadgame.enabled = true 인 경우 동작한다는 의미다!
 */
@ConditionalOnProperty(prefix = "my.handgame", name = ["enabled"], havingValue = "true")
class HandgameAutoconfiguration {

    @Bean
    /*
       handgame 빈이 존재하지 않을 경우, Handgame()을 로드하겠다는 의미다.
       (충돌될 수 있으므로)
     */
    @ConditionalOnMissingBean
    fun handgame() = Handgame()
}