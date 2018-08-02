package org.test.web.eventbus;

import com.google.common.eventbus.Subscribe;

public class SingleEventListener {

    public Integer lastInteger;

    @Subscribe
    public void listenInteger(Integer event) {
        lastInteger = event;
        System.out.println("single event Integer:" + lastInteger);
    }

    public Integer getLastInteger() {
        return lastInteger;
    }
}
