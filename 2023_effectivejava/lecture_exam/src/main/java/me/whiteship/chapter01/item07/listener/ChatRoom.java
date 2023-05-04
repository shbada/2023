package me.whiteship.chapter01.item07.listener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatRoom {

    /*
       WeakReference
       - Strong : 기존에 우리가 알고있는 인스턴스 생성 방식 및 오브젝트 할당
          - 메서드 내에서 선언했다면? Strong
       - Soft : new SoftReference<>(string); // string refenrence를 넣어줌
          - strong reference가 없고 soft reference만 남았을 경우에만 gc 대상 (메모리가 필요한 상황에서)
          - SoftReferenceExample.java
       - Weak : 약하게 연결되어있어서 거의 삭제됨
          - WeakReferenceExample.java
       - Phantom : 유령 reference라고 보자.
          - phantom이 남게된다.
          - PhantomReferenceExample.java

       List<WeakReference<User>>
       WeakReference들이 더이상 참조하지 않게되면, List에서 사라지겠구나? 라는 생각은 잘못된 생각이다.
       그대로 List 안에 있다.
       WeakHashMap 안에 들어있는 기능임
       만약에 List까지 삭제를 하려고한다면 커스텀한 리스트를 생성해야한다.
       WeakReference 자체가 참조하고있던건 삭제는 된다.
     */
    private List<WeakReference<User>> users; // 이렇게 쓰면 안된다. 올바른 WeakReference 사용법이 아니다.

    public ChatRoom() {
        this.users = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(new WeakReference<>(user));
    }

    public void sendMessage(String message) {
        users.forEach(wr -> Objects.requireNonNull(wr.get()).receive(message));
    }

    public List<WeakReference<User>> getUsers() {
        return users;
    }
}
