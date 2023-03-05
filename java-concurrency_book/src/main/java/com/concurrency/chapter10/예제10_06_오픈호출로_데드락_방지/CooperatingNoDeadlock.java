package com.concurrency.chapter10.예제10_06_오픈호출로_데드락_방지;

import java.util.*;

import com.concurrency.chapter04.예제4_06_스레드안전_추적_프로그램.Point;
import net.jcip.annotations.*;

/**
 * 오픈 호출
 * : 락을 전혀 확보하지 않은 상태에서 메서드를 호출하는 것
 *
 *- 메서드 호출 부분이 모두 오픈 호출로만 이뤄진 클래스는,
 * 락을 확보한 채로 메서드를 호출하는 클래스보다 훨씬 안정적이며, 다른 곳에서 불러다 쓰기도 좋다.
 *
 * 데드락을 방지하고자 오픈 호출을 사용하는 것은 스레드 안전성을 확보하기 위해 캡슐화 기법을 사용하는 것과 비슷하다.
 * 여러개의 락을 사용하는 프로그램의 코드 실행 경로를 쉽게 확인할 수 있다.
 *
 * 예제 10_05를 오픈호출을 사용하도록 리팩토링한 예제다.
 */

/**
 * CooperatingNoDeadlock
 * <p/>
 * Using open calls to avoiding deadlock between cooperating objects
 *
 * @author Brian Goetz and Tim Peierls
 */
class CooperatingNoDeadlock {
    @ThreadSafe
    class Taxi {
        @GuardedBy("this") private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        public synchronized void setLocation(Point location) {
            boolean reachedDestination;
            /**
             * synchronized 블록의 범위를 최소화한다.
             */
            synchronized (this) {
                this.location = location;
                reachedDestination = location.equals(destination);
            }

            if (reachedDestination)
                dispatcher.notifyAvailable(this);
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

    @ThreadSafe
    class Dispatcher {
        @GuardedBy("this") private final Set<Taxi> taxis;
        @GuardedBy("this") private final Set<Taxi> availableTaxis;

        public Dispatcher() {
            taxis = new HashSet<Taxi>();
            availableTaxis = new HashSet<Taxi>();
        }

        public synchronized void notifyAvailable(Taxi taxi) {
            availableTaxis.add(taxi);
        }

        public Image getImage() {
            Set<Taxi> copy;

            /**
             * synchronized 블록의 범위를 최소화한다.
             */
            synchronized (this) {
                copy = new HashSet<Taxi>(taxis);
            }

            Image image = new Image();
            for (Taxi t : copy)
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }

}
