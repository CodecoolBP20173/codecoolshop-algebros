package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.OrderJdbc;
import com.codecool.shop.util.NetworkUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/payment"})
public class Payment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/payment.html", context, resp.getWriter());
        HttpSession session = NetworkUtils.getHTTPSession(req);
        if (NetworkUtils.checkLoginStatus(session)) {
            String stringId = (String) session.getAttribute("userid");
            int userIntId = Integer.parseInt(stringId);
            OrderJdbc.addOrder(userIntId);
            OrderJdbc.removeFromOrder(userIntId);
        } else {
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (NetworkUtils.checkLoginStatus(session)) {
            String payPalName = req.getParameter("userName");
            String creditName = req.getParameter("name");
            if (payPalName != null || creditName != null) {
                resp.sendRedirect("/");
                session.invalidate();
            }
        } else {
            resp.sendRedirect("/");
        }
    }
}
