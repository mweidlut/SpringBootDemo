package org.test.web.eventbus;


import com.google.common.eventbus.EventBus;

public class Test {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus("test");
        SingleEventListener singleEventListener = new SingleEventListener();
        MultipleEventListener multiListener = new MultipleEventListener();
        DeadEventListener deadEventListener = new DeadEventListener();

        eventBus.register(singleEventListener);
        eventBus.register(multiListener);
        eventBus.register(deadEventListener);

        eventBus.post(100);
        eventBus.post(200);
        eventBus.post(300);

        eventBus.post(800L);
        eventBus.post(800990L);
        eventBus.post(800882934L);

        eventBus.post("hello world");

        System.out.println("LastInteger:" + multiListener.getLastInteger());
        System.out.println("LastLong:" + multiListener.getLastLong());
        System.out.println("DeadEvent:" + deadEventListener.isNotDelivered());
    }

}
