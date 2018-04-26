package com.codecool.shop.model;

import java.util.concurrent.atomic.AtomicInteger;

public class PaymentProcess extends AbstractProcess {
    private int processId;
    private static AtomicInteger idCounter = new AtomicInteger(10000);

    public PaymentProcess() {
        this.processId = idCounter.getAndIncrement();
    }

    protected void action(Orderable item) {
        item.pay(this);
    }
}
