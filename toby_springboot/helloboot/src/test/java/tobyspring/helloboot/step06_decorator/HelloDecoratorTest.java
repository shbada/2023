package tobyspring.helloboot.step06_decorator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloDecoratorTest {

    @Test
    void sayHello() {
        HelloDecorator helloDecorator = new HelloDecorator(name -> name);
        String ret = helloDecorator.sayHello("Test");

        Assertions.assertThat(ret).isEqualTo("*Test*");
    }
}