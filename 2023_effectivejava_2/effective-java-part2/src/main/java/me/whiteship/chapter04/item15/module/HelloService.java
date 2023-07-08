package me.whiteship.chapter04.item15.module;

import me.whiteship.name.NameService;

public class HelloService {

    public static void main(String[] args) {
        NameService service = new NameService();
        System.out.println(service.getName());
    }
}
