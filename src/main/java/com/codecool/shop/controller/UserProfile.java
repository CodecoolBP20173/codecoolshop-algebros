package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.Order;
import com.codecool.shop.processes.CheckoutProcess;
import com.codecool.shop.util.NetworkUtils;
import com.codecool.shop.util.SqlUserUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/user"})
public class UserProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session = NetworkUtils.getHTTPSession(req);
        String email = (String) session.getAttribute("email");
        HashMap<String, String> userInfo = SqlUserUtils.getUser(email);
        context.setVariable("userinfo", userInfo);
        engine.process("user/profile.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = NetworkUtils.getHTTPSession(req);
        String userid = (String) session.getAttribute("userid");
        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put("name", req.getParameter("name"));
        userInfo.put("email", req.getParameter("email"));
        userInfo.put("phone", req.getParameter("phone"));
        userInfo.put("zip", req.getParameter("zip"));
        userInfo.put("city", req.getParameter("city"));
        userInfo.put("country", req.getParameter("country"));
        userInfo.put("address", req.getParameter("address"));
        userInfo.put("userid", userid);
        SqlUserUtils.updateUser(userInfo);

        resp.sendRedirect("/");
    }

}
