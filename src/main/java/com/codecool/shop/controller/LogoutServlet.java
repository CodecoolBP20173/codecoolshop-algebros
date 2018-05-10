package com.codecool.shop.controller;


import com.codecool.shop.util.NetworkUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet  {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = NetworkUtils.getHTTPSession(req);
        session.invalidate();
    }
}
