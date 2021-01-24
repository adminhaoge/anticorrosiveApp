package com.play.mvp;

import org.junit.Test;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final static ReferenceQueue<GCTarget> REFERENCE_QUEUE = new ReferenceQueue<>();
    @Test
    public void addition_isCorrect() {
        LinkedList<GCTargetWeakReference> gcTarget = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            GCTarget gcTarget1 = new GCTarget(String.valueOf(i));
            GCTargetWeakReference weakReference = new GCTargetWeakReference(gcTarget1, REFERENCE_QUEUE);
            gcTarget.add(weakReference);
            System.out.println("Just created GCTargetWeakReference obj: " +
                    gcTarget.getLast());

        }
        System.gc();
        try {
            // 休息几分钟，等待上面的垃圾回收线程运行完成
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 检查关联的引用队列是否为空
        Reference<? extends GCTarget> reference;
        while((reference = REFERENCE_QUEUE.poll()) != null) {
            if(reference instanceof GCTargetWeakReference) {
                System.out.println("In queue, id is: " +
                        ((GCTargetWeakReference) (reference)).id);
            }
        }
    }
}