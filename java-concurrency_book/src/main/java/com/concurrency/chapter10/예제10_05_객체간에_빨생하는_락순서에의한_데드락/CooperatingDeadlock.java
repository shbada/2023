package com.concurrency.chapter10.예제10_05_객체간에_빨생하는_락순서에의한_데드락;

import java.util.*;

import com.concurrency.chapter04.예제4_06_스레드안전_추적_프로그램.Point;
import net.jcip.annotations.*;

/**
 * 두개의 스레드에서 두개의 락을 서로 다른 순서로 가져가려는 상황
 * 즉, 데드락이 발생한다.
 */

/**
 * CooperatingDeadlock
 * <p/>
 * Lock-ordering deadlock between cooperating objects
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CooperatingDeadlock {
    // Warning: deadlock-prone!
    class Taxi {
        @GuardedBy("this") private Point location, destination;
        private final Dispatcher dispatcher;

        public Taxi(Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        public synchronized Point getLocation() {
            return location;
        }

        /**
         * 1) 첫번째락 : Taxi
         * 2) 두번째락 : Dispatcher
         * @param location
         */
        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination))
                dispatcher.notifyAvailable(this);
        }

        public synchronized Point getDestination() {
            return destination;
        }

        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
    }

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

        /**
         * 1) 첫번째락 : Dispatcher
         * 2) 두번째락 : Taxi
         * @return
         */
        public synchronized Image getImage() {
            Image image = new Image();
            for (Taxi t : taxis)
                image.drawMarker(t.getLocation());
            return image;
        }
    }

    class Image {
        public void drawMarker(Point p) {
        }
    }
}
