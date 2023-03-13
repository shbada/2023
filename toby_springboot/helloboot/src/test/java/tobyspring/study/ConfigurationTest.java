package tobyspring.study;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    void configuration() {
//        MyConfig myConfig = new MyConfig();
//        Bean1 bean1 = myConfig.bean1();
//        Bean2 bean2 = myConfig.bean2();

        // 같은가? -> 다르다.
//        Assertions.assertThat(bean1.common).isSameAs(bean2.common);

        // 이때 MyConfig 를 Spring 컨테이너에 등록
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        Bean1 springBean1 = ac.getBean(Bean1.class);
        Bean2 springBean2 = ac.getBean(Bean2.class);

        // 같다.
        Assertions.assertThat(springBean1.common).isSameAs(springBean2.common);
    }

    @Test
    @DisplayName("직접 proxy 를 만들어서 common 객체가 동일하다.")
    void proxyCommonMethod() {
        MyConfigProxy proxy = new MyConfigProxy();

        Bean1 bean1 = proxy.bean1();
        Bean2 bean2 = proxy.bean2();

        Assertions.assertThat(bean1.common).isSameAs(bean2.common);
    }

    static class MyConfigProxy extends MyConfig {
        private Common common;

        @Override
        Common common() {
            if (this.common == null) {
                this.common = super.common();
            }

            return this.common;
        }
    }

    @Configuration // proxy를 만든다.
    static class MyConfig {
        @Bean
        Common common() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(common());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(common());
        }
    }

    @Configuration(proxyBeanMethods = false) // proxy를 만들지 않는다.
    static class MyConfig2 {
        @Bean
        Common common2() {
            return new Common();
        }

//        @Bean
//        Bean1 bean3() {
//            // proxyBeanMethods = false 일때 다른 Bean 오브젝트를 직접 호출하면 위험하다는 경고다.
//            return new Bean1(common2());
//        }
//
//        @Bean
//        Bean2 bean4() {
//            return new Bean2(common2());
//        }
    }

    // Bean1, Bean2 는 싱글톤 (동일한 오브젝트)
    // Bean1 <-- Common
    // Bean2 <-- Common

    static class Bean1 {
        private final Common common;

        Bean1(Common common) {
            this.common = common;
        }
    }

    static class Bean2 {
        private final Common common;

        Bean2(Common common) {
            this.common = common;
        }
    }

    static class Common {

    }
}
