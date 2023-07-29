package com.reactive.step08;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class E38_RemoteServiceApplication {
    @Bean
    TomcatReactiveWebServerFactory tomcatReactiveWebServerFactory() {
        return new TomcatReactiveWebServerFactory();
    }

    @RestController
    public static class RemoteController {
        @GetMapping("/step08/service")
        public String service(String req) throws InterruptedException {
            Thread.sleep(2000);
            return req + "/service";
        }

        /**
         * service 2 추가
         */
        @GetMapping("/step08/service2")
        public String service2(String req) throws InterruptedException {
            return req + "/service2";
        }
    }

    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("server.tomcat.max-threads", "1000");
        SpringApplication.run(E38_RemoteServiceApplication.class, args);
    }
}
