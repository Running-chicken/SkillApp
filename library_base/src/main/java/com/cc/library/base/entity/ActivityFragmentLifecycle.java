package com.cc.library.base.entity;

import com.bumptech.glide.util.Util;
import com.cc.library.base.interfaces.Lifecycle;
import com.cc.library.base.interfaces.LifecycleListener;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class ActivityFragmentLifecycle implements Lifecycle {

    private final Set<LifecycleListener> lifecycleListeners =
            Collections.newSetFromMap(new WeakHashMap<LifecycleListener,Boolean>());

    private boolean isStarted;
    private boolean isDestroyed;

    @Override
    public void addListener(LifecycleListener lifecycleListener) {
        lifecycleListeners.add(lifecycleListener);
    }

    public void onStart(){
        isStarted = true;
        for(LifecycleListener lifecycleListener:Util.getSnapshot(lifecycleListeners)){
            lifecycleListener.onStart();
        }
    }


    public void onStop() {
        isStarted = false;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(lifecycleListeners)) {
            lifecycleListener.onStop();
        }
    }

    public void onDestroy() {
        isDestroyed = true;
        for (LifecycleListener lifecycleListener : Util.getSnapshot(lifecycleListeners)) {
            lifecycleListener.onDestroy();
        }
    }
}
