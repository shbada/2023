package me.whiteship.hello;

import me.whiteship.name.NameService;

public class HelloService {

    public static void main(String[] args) {
        // 모듈 사용 (NameService)
        NameService service = new NameService();
        System.out.println("Hello " + service.getName() + ", Java Module");
    }
}
