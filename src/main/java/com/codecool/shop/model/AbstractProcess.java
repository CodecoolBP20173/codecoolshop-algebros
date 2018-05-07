package com.codecool.shop.model;

public abstract class AbstractProcess {
    public void process(Order order) {
        stepBefore();
        action(order);
        stepAfter();
    }

    public void stepBefore() {
        System.out.println("Process started.");
    }

    protected abstract void action(Order order);

    public void stepAfter() {
        System.out.println("Process ended succesfully.");
    }
}
