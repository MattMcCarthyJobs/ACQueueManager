package com.twiage.acqueuemanager;

import java.util.Date;

public class Aircraft implements Comparable<Aircraft> {

    private final ACType acType;
    private final ACSize acSize;
    private Date enqueueTime;

    public Aircraft(ACType acType, ACSize acSize) {
        this.acType = acType;
        this.acSize = acSize;
    }
    
    public enum ACType {
        Passenger,
        Cargo
    }

    public enum ACSize {
        Large,
        Small
    }

    @Override
    public int compareTo(Aircraft aircraft) {
        final Integer BEFORE = -1;
        final Integer EQUAL = 0;
        final Integer AFTER = 1;
        if(getACType() == ACType.Passenger && aircraft.getACType() == ACType.Cargo) {
            return BEFORE;
        } 
        if(getACType() == ACType.Cargo && aircraft.getACType() == ACType.Passenger) {
            return AFTER;
        } 
        if(getACSize() == ACSize.Large && aircraft.getACSize() == ACSize.Small) {
            return BEFORE;
        } 
        if(getACSize() == ACSize.Small && aircraft.getACSize() == ACSize.Large) {
            return AFTER;
        } 
        if(getEnqueueTime().before(aircraft.getEnqueueTime())) {
            return BEFORE;
        }
        if(getEnqueueTime().after(aircraft.getEnqueueTime())) {
            return AFTER;
        }
        return EQUAL;
    }

    public ACType getACType() {
        return acType;
    }

    public ACSize getACSize() {
        return acSize;
    }

    public Date getEnqueueTime() {
        return enqueueTime;
    }

    public void setEnqueueTime(Date enqueueTime) {
        this.enqueueTime = enqueueTime;
    }

}
