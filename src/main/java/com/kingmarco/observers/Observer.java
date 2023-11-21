package com.kingmarco.observers;

import com.kingmarco.forge.GameObject;
import com.kingmarco.observers.events.Event;

public interface Observer {
    void onNotify(GameObject object, Event event);
}
