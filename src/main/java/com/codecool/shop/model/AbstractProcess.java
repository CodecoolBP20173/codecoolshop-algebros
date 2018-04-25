package com.codecool.shop.model;

public abstract class AbstractProcess {
    public void process(Orderable item) {
        stepBefore();
        action(item);
        stepAfter();
    }

    public void stepBefore() {
        System.out.println("Process started.");
    }

    protected abstract void action(Orderable item);

    public void stepAfter() {
        System.out.println("Process ended succesfully.");
    }
}
