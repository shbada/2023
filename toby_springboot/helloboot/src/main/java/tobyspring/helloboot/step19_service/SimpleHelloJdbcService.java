package tobyspring.helloboot.step19_service;

import org.springframework.stereotype.Service;
import tobyspring.helloboot.step18_repository.HelloRepository;

@Service
public class SimpleHelloJdbcService implements HelloJdbcService {
    private final HelloRepository helloRepository;

    public SimpleHelloJdbcService(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    public String sayHello(String name) {
        this.helloRepository.increaseCount(name);

        return "Hello " + name;
    }

    @Override
    public int countOf(String name) {
        return helloRepository.countOf(name);
    }
}

