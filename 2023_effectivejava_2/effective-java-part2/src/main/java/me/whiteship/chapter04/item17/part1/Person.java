package me.whiteship.chapter04.item17.part1;

public final class Person {

    /**
     * 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다.
     */
    private final Address address;

    public Person(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        // 복사본을 만든다.
        Address copyOfAddress = new Address();
        copyOfAddress.setStreet(address.getStreet());
        copyOfAddress.setZipCode(address.getZipCode());
        copyOfAddress.setCity(address.getCity());
        return copyOfAddress;

        // 이렇게하면 set 메서드로 값 변경이 가능해져버린다. (address 내부 필드)
//        return address;
    }

    public static void main(String[] args) {
        Address seattle = new Address();
        seattle.setCity("Seattle");

        Person person = new Person(seattle);

        Address redmond = person.getAddress();
        redmond.setCity("Redmond");

        System.out.println(person.address.getCity());
    }
}
