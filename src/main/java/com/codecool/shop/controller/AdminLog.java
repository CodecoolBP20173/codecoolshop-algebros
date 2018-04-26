package com.codecool.shop.controller;

import com.codecool.shop.model.Order;

import org.json.simple.JSONArray;
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

    public void initialLog(int orderId) {
        fileName = String.valueOf(orderId) + "-" +  LocalDate.now() + ".json";
        orderLog.put(LocalDateTime.now(), "Order, with ID: " + String.valueOf(orderId) + " created.");
        try (FileWriter adminLog = new FileWriter(fileName, true)) {
            adminLog.write(orderLog.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logToFile() {
        try (FileWriter adminLog = new FileWriter(fileName, true)) {
            adminLog.append(orderLog.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
