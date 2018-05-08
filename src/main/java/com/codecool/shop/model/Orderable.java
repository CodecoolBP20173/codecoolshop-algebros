package com.codecool.shop.model;

import com.codecool.shop.processes.CheckoutProcess;
import com.codecool.shop.processes.PaymentProcess;

public interface Orderable {
    boolean checkout(CheckoutProcess checkoutProcess);
    boolean pay(PaymentProcess paymentProcess);
}
