package com.twiage.acqueuemanager.tests;

import com.twiage.acqueuemanager.Aircraft;
import com.twiage.acqueuemanager.ACQueueManager;
import com.twiage.acqueuemanager.Aircraft.ACSize;
import com.twiage.acqueuemanager.Aircraft.ACType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.Before;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class TestACQueueManagerPerformance {

    private ACQueueManager acQueueManager;

    @Before
    public void setup() {
        acQueueManager = mock(ACQueueManager.class, CALLS_REAL_METHODS);
        acQueueManager.boot();
    }

    @Test
    public void performanceTest() {
        //Arrange
        Date start = new Date();
        while (acQueueManager.getACQueue().size() < 10000) {
            Aircraft aircraft = getRandomAircraft();
            acQueueManager.enqueue(aircraft);
        }
        System.out.println("Enqueued in: "+computeDiff(start, new Date()));

        start = new Date();

        //Act
        while (acQueueManager.getACQueue().size() > 0) {
            Aircraft aircraft = acQueueManager.dequeue();
            System.out.println("Dequeued: "+aircraft.getACType()+"/"+aircraft.getACSize()+"/"+getFormattedTime(aircraft.getEnqueueTime()));
        }

        //Assert
        System.out.println("Dequeued in: "+computeDiff(start, new Date()));
    }

    private Aircraft getRandomAircraft() {
        Random random = new Random();
        ACType randomACType;
        ACSize randomACSize;
        if (random.nextInt(2) == 0) {
            randomACType = ACType.Passenger;
        } else {
            randomACType = ACType.Cargo;
        }
        if (random.nextInt(2) == 0) {
            randomACSize = ACSize.Large;
        } else {
            randomACSize = ACSize.Small;
        }
        return new Aircraft(randomACType, randomACSize);
    }

    public static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
        long diffInMilliSeconds = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);
        Map<TimeUnit,Long> result = new LinkedHashMap<>();
        long milliSecondsRest = diffInMilliSeconds;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliSecondsRest,TimeUnit.MILLISECONDS);
            long diffInMilliSecondsForUnit = unit.toMillis(diff);
            milliSecondsRest = milliSecondsRest - diffInMilliSecondsForUnit;
            result.put(unit,diff);
        }
        return result;
    }
    
    private String getFormattedTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        return simpleDateFormat.format(date);
    }
}
