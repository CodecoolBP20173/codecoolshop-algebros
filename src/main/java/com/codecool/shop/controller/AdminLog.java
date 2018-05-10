package com.codecool.shop.controller;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminLog {
    private JSONObject orderLog = new JSONObject();
    private String fileName;

    public AdminLog(int orderId) {
        initialLog(orderId);
    }

    public JSONObject getOrderLog() {
        return orderLog;
    }

    private void initialLog(int orderId) {
        orderLog.put(LocalDateTime.now(), "Order, with ID: " + String.valueOf(orderId) + " created.");
        this.logToFile(orderId);
    }

    public void logStringToAdminLog(int orderId, String log) {
        orderLog.put(LocalDateTime.now(), log);
        this.logToFile(orderId);
    }


    private void logToFile(int orderId) {
        fileName = String.valueOf(orderId) + "-" + LocalDate.now() + ".json";
        try (FileWriter adminLog = new FileWriter(fileName, true)) {
            adminLog.append(orderLog.toJSONString());
            orderLog.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
