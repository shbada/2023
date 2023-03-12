package tobyspring.helloboot.step02_di;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 컨테이너 없이 작성하면 매우 빠르다.
 */
class HelloControllerTest {

    @Test
    void hello() {
        HelloController helloController = new HelloController(name -> name);
//        HelloController helloController = new HelloController(new HelloService() {
//            @Override
//            public String sayHello(String name) {
//                return name;
//            }
//        });

        String ret = helloController.hello("Test");
        Assertions.assertThat(ret).isEqualTo("Test");
    }

    @Test
    void failHelloController() {
        HelloController helloController = new HelloController(name -> name);

        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello(null);
        }).isInstanceOf(NullPointerException.class);

        // 빈문자열의 경우
        Assertions.assertThatThrownBy(() -> {
            String ret = helloController.hello("");
        }).isInstanceOf(NullPointerException.class);
    }
}