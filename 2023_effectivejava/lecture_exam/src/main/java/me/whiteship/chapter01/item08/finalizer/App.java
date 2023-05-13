package me.whiteship.chapter01.item08.finalizer;

import com.sun.management.UnixOperatingSystemMXBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Field;

public class App {

    /**
     * 코드 참고 https://www.baeldung.com/java-finalize
     */
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int i = 0;
        while(true) {
            i++;
            // gc 대상 객체가 계속 만들어짐
            new FinalizerIsBad(); // 이 객체를 계속 만든다.

            // 100만개 만들어졌을때
            /*
            final인 java.lang.ref.Finalizer 클래스 안에 있는 ReferenceQueue 안에 들어간다.
            gc 대상이 되면 finalize()를 실행해주는게 위의 큐에 들어간다.
             */
            if ((i % 1_000_000) == 0) {
                Class<?> finalizerClass = Class.forName("java.lang.ref.Finalizer");
                Field queueStaticField = finalizerClass.getDeclaredField("queue");
                queueStaticField.setAccessible(true);
                ReferenceQueue<Object> referenceQueue = (ReferenceQueue) queueStaticField.get(null);

                Field queueLengthField = ReferenceQueue.class.getDeclaredField("queueLength");
                queueLengthField.setAccessible(true);
                // queue에 얼마나 많은 오브젝트가 들어가는지 확인하는 예제
                long queueLength = (long) queueLengthField.get(referenceQueue);
                System.out.format("There are %d references in the queue%n", queueLength);
            }
        }
    }
}
