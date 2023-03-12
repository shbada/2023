package tobyspring.helloboot.step04_component;

import org.springframework.stereotype.Component;

/**
 * DI (Dependency Injection)
 *
 * HelloController -> SimpleHelloService
 * HelloController 는 SimpleHelloService 의 수정 발생에 대해 영향을 받는다. (의존)
 * 또다른 ComplexHelloService.java 가 추가됬다고 가정할때,
 * SimpleHelloService 대신 ComplexHelloService 를 적용하려고하면 HelloController 코드 수정이 발생한다.
 * (new SimpleHelloService(); -> new ComplexHelloService();)
 *
 * 위 문제를 해결하기 위해서 아래와 같은 방법을 사용한다.
 * HelloController -> HelloService(interface) <- SimpleHelloService, ComplexHelloService (구현클래스)
 * HelloService 의 구현클래스가 아무리 많아져도, HelloController의 수정이 발생하지 않게된다.
 * 근데,, 소스코드 레벨에서는 괜찮다해도, 런타임때 어떠한 클래스의 오브젝트를 이용할지를 정해져야한다. (결정 필요)
 *
 * 위 과정을 DI라고 한다.
 * 이 주입을 위해 제 3의 역할이 Assembler 이다.
 * 런타임에 필요하다면 이용할 구현 클래스를 선택해야 하는데, 이를 외부에서 HelloController 가 사용할 수 있도록 주입해준다.
 * 서로 관계를 연결시켜주고 사용할 수 있도록 한다.
 * --> Assembler = Spring Container
 *
 * (방법)
 * 1) HelloController 를 만들때 생성자 파라미터로 구현 클래스를 넣는다.
 * 2) Factory 메서드로 빈을 만들도록 해서 파라미터로 구현 클래스를 넘긴다.
 * 3) HelloController 에서 set 메서드로 구현 클래스를 셋팅한다.
 */
@Component
public class SimpleHelloService implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
