package com.play.mvp;

public class GCTarget {
    public String id;
    byte[] buffer = new byte[1024];

    public GCTarget(String id) {
        this.id = id;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Finalizing GCTarget, id is : " + id);
    }
}
