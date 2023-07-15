package me.whiteship.chapter04.item18.callback;

class BobFunction implements FunctionToCall {

    private final Service service;

    public BobFunction(Service service) {
        this.service = service;
    }

    @Override
    public void call() {
        System.out.println("밥을 먹을까..");
    }

    @Override
    public void run() {
        // 객체를 넘겨서 service 안에서 필요할때 해당 객체의 메서드를 호출한다.
        this.service.run(this); // 자기자신을 전달
        // 위에서 self의 문제
        // Wrapper Class가 아닌 this(self) 인스턴스가 넘겨짐
        // 자기를 누가 감싸고있는지 모르기때문에 자기자신을 넘긴다.
    }
}
