package net.nakshay.wstp.internal.pool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WSTPFactoryTest {

    @Test
    void createDefaultPoolTest(){
        WSTPFactory.defaultThreadPool();
    }

    @Test
    void createCustomPoolTest(){
        WSTPFactory.newThreadPool(10);
    }
}
