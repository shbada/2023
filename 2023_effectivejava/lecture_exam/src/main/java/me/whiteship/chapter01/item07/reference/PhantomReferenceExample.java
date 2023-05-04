package me.whiteship.chapter01.item07.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceExample {

    public static void main(String[] args) throws InterruptedException {
        BigObject strong = new BigObject();
        ReferenceQueue<BigObject> rq = new ReferenceQueue<>(); // ReferenceQueue 를 써야한다.

        // 본래 object는 정리를 하고, phantom 오브젝트를 referenceQueue에 넣어준다. 바로 없애지 않고 queue에 넣는 것이다.
        BigObjectReference<BigObject> phantom = new BigObjectReference<>(strong, rq);
        strong = null;

        System.gc();
        Thread.sleep(3000L);

        // TODO 팬텀은 유령이니까..
        //  죽었지만.. 사라지진 않고 큐에 들어갑니다.
        System.out.println(phantom.isEnqueued()); // 큐에 있는지 체크 가능

        Reference<? extends BigObject> reference = rq.poll();
        // 자원을 반납하는 용도로 쓰려면 커스텀한 Reference를 생성한다.
        BigObjectReference bigObjectCleaner = (BigObjectReference) reference;
        bigObjectCleaner.cleanUp();
        reference.clear(); // 최종적으로 Phantom reference가 사라진다.
    }
}
