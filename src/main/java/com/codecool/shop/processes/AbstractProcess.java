package com.codecool.shop.processes;

import com.codecool.shop.model.Order;

public abstract class AbstractProcess {
    public void process(Order order) {
        stepBefore();
        action(order);
        stepAfter();
    }

    private void stepBefore() {
        System.out.println("Process started.");
    }

    protected abstract void action(Order order);

    private void stepAfter() {
        System.out.println("Process ended successfully.");
    }
}
