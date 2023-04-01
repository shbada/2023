package me.whiteship.chapter01.item01.step01;

import me.whiteship.chapter01.item01.step04_enum.Product;

public class Order {

    private boolean prime;

    private boolean urgent;

    private Product product;

    private OrderStatus orderStatus;

//    public Order(boolean prime, boolean urgent, Product product, OrderStatus orderStatus) {
//        this.prime = prime;
//        this.urgent = urgent;
//        this.product = product;
//        this.orderStatus = orderStatus;
//    }

    /**
     * 아래 두 생성자는 파라미터 타입이 동일한 prime, urgent 때문에 선언할 수 없다.
     */
//    public Order(boolean prime, Product product, OrderStatus orderStatus) {
//        this.prime = prime;
//        this.urgent = urgent;
//        this.product = product;
//        this.orderStatus = orderStatus;
//    }
//
//    public Order(boolean urgent, Product product, OrderStatus orderStatus) {
//        this.prime = prime;
//        this.urgent = urgent;
//        this.product = product;
//        this.orderStatus = orderStatus;
//    }

    /**
     * 생성자 -> 정적팩토리 메서드
     * 1. 메서드 명을 지정할 수 있다. (객체의 특징을 좀더 분명하게 표현할 수 있다.)
     */
    public static Order primeOrder(Product product) {
        Order order = new Order();
        order.prime = true;
        order.product = product;

        return order;
    }

    public static Order urgentOrder(Product product) {
        Order order = new Order();
        order.urgent = true;
        order.product = product;
        return order;
    }

    public static void main(String[] args) {

        Order order = new Order();
        if (order.orderStatus == OrderStatus.DELIVERED) {
            System.out.println("delivered");
        }
    }

}
