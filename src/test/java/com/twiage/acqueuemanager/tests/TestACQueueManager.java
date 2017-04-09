package com.twiage.acqueuemanager.tests;

import com.twiage.acqueuemanager.Aircraft;
import com.twiage.acqueuemanager.ACQueueManager;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.junit.Before;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class TestACQueueManager {

    private ACQueueManager acQueueManager;

    @Before
    public void setup() {
        acQueueManager = mock(ACQueueManager.class, CALLS_REAL_METHODS);
        acQueueManager.boot();
    }

    @Test
    public void bootTest() {
        //Arrange
        final Integer expectedACQueueSize = 0;

        //Act
        
        //Assert
        assertThat(acQueueManager.getACQueue()).isNotNull();
        assertThat(acQueueManager.getACQueue().size()).isEqualTo(expectedACQueueSize);
    }

    @Test
    public void enqueueTest() {
        //Arrange
        final Integer expectedACQueueSize = 1;

        Aircraft mockAircraft = mock(Aircraft.class);

        //Act
        acQueueManager.enqueue(mockAircraft);

        //Assert
        assertThat(acQueueManager.getACQueue().size()).isEqualTo(expectedACQueueSize);
    }

}
