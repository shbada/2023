package me.whiteship.chapter01.item01.step03._5번째장점;

import me.whiteship.chapter01.item01.step03.HelloService;
//import me.whiteship.hello.ChineseHelloService;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.ServiceLoader;

public class HelloServiceFactory {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        /**
         * load - 서비스 접근 API
         */
        /* 참조할 수 있는 classpath 내의 HelloService 구현체를 가져온다. */
        ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class);
        /* 첫번재껄 찾는다. 없을수도 있으므로 Optional 로 받는다. */
        Optional<HelloService> helloServiceOptional = loader.findFirst();

        /*
            정적 팩토리 메서드가 있는 경우에, 인터페이스만 있고 구현체가 없어도 된다고 했다.
            ChineseHelloService 가 조회된다.

            chinese-hello-service 프로젝트 내에 ChineseHelloService.java 파일이 있다.
            META-INF/services 경로에 HelloService를 구현한 구체클래스 경로를 넣었다.
            (파일명 : me.whiteship.chapter01.item01.step03.HelloService -> 경로)
            이렇게 함으로써 등록되어 jar 패키징할때 이 정보를 jar 파일 안에 들어가게된다. (서비스 등록 API)
            그리고 현재 프로젝트에서 위 chinese-hello-service 를 pom.xml에서 의존하고있다. (그래서 참조가 가능하다)

            왜 이렇게 사용할까?
            위와 같은 코드는 ChineseHelloService에 의존성이 없다.
            (new ChineseHelloService();를 할 경우 import가 추가되면서 의존성이 생긴다.)
         */

        /* 있을때 hello() 수행 */
        helloServiceOptional.ifPresent(h -> {
            System.out.println(h.hello());
        });

//        HelloService helloService = new ChineseHelloService();
//        System.out.println(helloService.hello());

//        Class<?> aClass = Class.forName("me.whiteship.hello.ChineseHelloService");
//        Constructor<?> constructor = aClass.getConstructor();
//        HelloService helloService = (HelloService) constructor.newInstance();
//        System.out.println(helloService.hello());
    }

}
