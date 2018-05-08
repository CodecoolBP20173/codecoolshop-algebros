package com.codecool.shop.util;

import com.codecool.shop.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class NetworkUtils {
    public static HttpSession getHTTPSession(HttpServletRequest req) {
        HttpSession session;
        if (req.getSession(false) == null  ){
            session = req.getSession(true);
            session.setAttribute("Order", new Order());
        } else {
            session = req.getSession();
        }
        return session;
    }

}
