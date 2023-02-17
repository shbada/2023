package com.concurrency.chapter04.예제4_09_두개이상의변수에게_스레드안정성_위임;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.concurrent.*;

/**
 * VisualComponent
 * <p/>
 * Delegating thread safety to multiple underlying state variables
 *
 * @author Brian Goetz and Tim Peierls
 *
 * 두개 이상의 변수에게 스레드 안정성을 위임
 * 두개 이상의 변수가 서로 독립적이라면 클래스의 스레드 안정성을 위임할 수 있다.
 * 독립적이라는 의미는 변수가 서로의 상태 값에 대한 연관성이 없다는 말이다.
 */
public class VisualComponent {
    /*
       아래 2개의 변수가 서로 독립적이다.
       VisualComponent 클래스는 스레드 안전한 두개의 이벤트 리스너 목록에게 클래스의 스레드 안정성을 위임할 수 있다.

       CopyOnWriteArrayList에 이벤트 리스너 목록을 보관한다.
       CopyOnWriteArrayList는 리스너 목록을 관리하기에 적당히 만들어져있는 스레드 안전한 List 클래스다.

       VisualComponent에서 사용하는 두가지 List가 모두 스레드 안전성을 확보하고 있고, 그 두개의 변수를 서로
       연동시켜 묶어주는 상태가 전혀 없기 때문에 VisualComponent 는 스레드 안정성이라는 책임을
       mouseListeners와 keyListeners 변수에게 완전히 위임할 수 있다.
    */
    /* 키보드 이벤트를 관리하는 목록 변수 */
    private final List<KeyListener> keyListeners = new CopyOnWriteArrayList<KeyListener>();
    /* 마우스 이벤트를 관리하는 목록 변수 */
    private final List<MouseListener> mouseListeners = new CopyOnWriteArrayList<MouseListener>();

    public void addKeyListener(KeyListener listener) {
        keyListeners.add(listener);
    }

    public void addMouseListener(MouseListener listener) {
        mouseListeners.add(listener);
    }

    public void removeKeyListener(KeyListener listener) {
        keyListeners.remove(listener);
    }

    public void removeMouseListener(MouseListener listener) {
        mouseListeners.remove(listener);
    }
}
