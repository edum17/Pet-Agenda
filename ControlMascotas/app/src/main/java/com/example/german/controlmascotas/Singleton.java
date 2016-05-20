package com.example.german.controlmascotas;
import android.content.Context;
/**
 * Created by German on 20/05/2016.
 */
public class Singleton {
    private static Singleton mInstance;
    private Context context;
    private int containerId;

    public static Singleton getInstance() {
        if (mInstance == null) mInstance = getSync();
        return mInstance;
    }

    private static synchronized Singleton getSync() {
        if (mInstance == null) mInstance = new Singleton();
        return mInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setContainerId(int containerId) {
        this.containerId = containerId;
    }
}
