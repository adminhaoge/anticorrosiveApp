package com.play.mvp;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class GCTargetWeakReference extends WeakReference<GCTarget> {
    public String id;
    public GCTargetWeakReference(GCTarget referent, ReferenceQueue<? super GCTarget> q) {
        super(referent, q);
        this.id = referent.id;
    }

    protected void finalize() {
        System.out.println("Finalizing GCTargetWeakReference " + id);
    }
}
