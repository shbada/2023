package com.example.SessionStore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * [Redis를 사용하지 않고 HashMap을 사용했을 경우]
 *
 * cd libs
 * java -Dserver.port=8081 -jar SessionStore-0.0.1-SNAPSHOT.jar
 *
 * port 가 다른 서비스 2개가 있을때
 * localhost (같은 도메인)으로 브라우저에서 JSESSIONID를 보내지만 실제 8080엔 있지만 8081엔 없을 수 있다.
 *
 * [Redis 사용]
 * - HttpSession이 각 서비스별로 공유 필요
 * - application.yml 파일 내에 'spring 하위 session 하위에 storage-type: redis'로 지정
 * - 위와같이 설정을 추가하면 각각의 session의 관리되는 정보가 redis에 저장된다.
 * - 추가로, build.gradle 내에 'implementation 'org.springframework.session:spring-session-data-redis' 의존성 추가해줘야한다.
 * - redis session을 사용하게되면 쿠키이름이 SESSION=**으로 변경된다.
 */
@RestController
public class LoginController {

//    HashMap<String, String> sessionMap = new HashMap<>();

    /**
     * 로그인
     * @param session
     * @param name
     * @return
     */
    @GetMapping("/login")
    public String login(HttpSession session, @RequestParam String name) {
        session.setAttribute("name", name);

        return "saved.";
    }

    /**
     * 세션 조회
     * @param session
     * @return
     */
    @GetMapping("/myName")
    public String myName(HttpSession session) {
        // 브라우저가 보낸 JSESSIONID로 객체가 들어가서 조회됨
        String myName = (String) session.getAttribute("name");

        return myName;
    }
}
