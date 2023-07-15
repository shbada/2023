package me.whiteship.chapter04.item18.callback;

public class Service {

    public void run(FunctionToCall functionToCall) {
        System.out.println("뭐 좀 하다가...");

        // 언젠가 호출
        functionToCall.call();
    }

    public static void main(String[] args) {
        Service service = new Service();
        BobFunction bobFunction = new BobFunction(service);

        // wrapper
        BobFunctionWrapper bobFunctionWrapper = new BobFunctionWrapper(bobFunction);
        bobFunctionWrapper.run();

        // 원하는대로 하려면 위 run() 호출로직을 주석하고 아래 call()을 호출할 수 밖에 없음
        bobFunctionWrapper.call();
    }
}
