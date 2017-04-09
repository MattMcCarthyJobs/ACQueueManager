package com.twiage.acqueuemanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ACQueueManager {
    private List<Aircraft> acQueue;
    
    public void boot() {
        acQueue = new ArrayList<>();
    }
    
    public void enqueue(Aircraft aircraft) {
        aircraft.setEnqueueTime(new Date());
        acQueue.add(aircraft);
    }
    
    public Aircraft dequeue() {
        Collections.sort(acQueue);
        return acQueue.remove(0);
    }

    public List<Aircraft> getACQueue() {
        return acQueue;
    }
}
