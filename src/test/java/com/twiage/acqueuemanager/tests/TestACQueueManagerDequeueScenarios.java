package com.twiage.acqueuemanager.tests;

import com.twiage.acqueuemanager.Aircraft;
import com.twiage.acqueuemanager.Aircraft.ACSize;
import com.twiage.acqueuemanager.Aircraft.ACType;
import com.twiage.acqueuemanager.ACQueueManager;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class TestACQueueManagerDequeueScenarios {

    private ACQueueManager acQueueManager;
    private final Aircraft aircraft1;
    private final Aircraft aircraft2;
    private final Aircraft aircraft3;
    private final Aircraft aircraft4;
    private Integer expectedRemovedIndex = 0;

    public TestACQueueManagerDequeueScenarios(ACType aircraft1Type, ACSize aircraft1Size, ACType aircraft2Type, ACSize aircraft2Size, ACType aircraft3Type, ACSize aircraft3Size, ACType aircraft4Type, ACSize aircraft4Size, Integer expectedRemovedIndex) {
        aircraft1 = new Aircraft(aircraft1Type, aircraft1Size);
        aircraft2 = new Aircraft(aircraft2Type, aircraft2Size);
        aircraft3 = new Aircraft(aircraft3Type, aircraft3Size);
        aircraft4 = new Aircraft(aircraft4Type, aircraft4Size);
        this.expectedRemovedIndex = expectedRemovedIndex;
    }

    @Parameterized.Parameters
    public static Collection createAircraft() {
        return Arrays.asList(new Object[][]{
            {ACType.Passenger, ACSize.Large, ACType.Passenger, ACSize.Small, ACType.Cargo, ACSize.Large, ACType.Cargo, ACSize.Small, 0},
            {ACType.Cargo, ACSize.Small, ACType.Passenger, ACSize.Large, ACType.Passenger, ACSize.Small, ACType.Cargo, ACSize.Large, 1},
            {ACType.Cargo, ACSize.Large, ACType.Cargo, ACSize.Small, ACType.Passenger, ACSize.Large, ACType.Passenger, ACSize.Small, 2},
            {ACType.Passenger, ACSize.Small, ACType.Cargo, ACSize.Large, ACType.Cargo, ACSize.Small, ACType.Passenger, ACSize.Large, 3},
            {ACType.Passenger, ACSize.Small, ACType.Passenger, ACSize.Small, ACType.Passenger, ACSize.Small, ACType.Passenger, ACSize.Small, 0},
        });
    }

    @Before
    public void setup() {
        acQueueManager = mock(ACQueueManager.class, CALLS_REAL_METHODS);
        acQueueManager.boot();
    }

    @Test
    public void dequeueTest() {
        //Arrange
        final Integer expectedACQueueSize = 3;

        acQueueManager.enqueue(aircraft1);
        acQueueManager.enqueue(aircraft2);
        acQueueManager.enqueue(aircraft3);
        acQueueManager.enqueue(aircraft4);

        Aircraft expectedRemovedAircraft = acQueueManager.getACQueue().get(expectedRemovedIndex);

        //Act
        Aircraft actualAircraft = acQueueManager.dequeue();

        //Assert
        assertThat(actualAircraft).isEqualTo(expectedRemovedAircraft);
        assertThat(acQueueManager.getACQueue().size()).isEqualTo(expectedACQueueSize);
    }
}
