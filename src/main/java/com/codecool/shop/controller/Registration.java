package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.util.HashUtils;
import com.codecool.shop.util.SqlUserUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {
    private static HashMap<String, String > userInfo;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("user/registration.html", context, resp.getWriter());
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        userInfo = new HashMap<>();
        userInfo.put("name",req.getParameter("Username"));
        userInfo.put("email",req.getParameter("Email"));
        hashUserInfo(req.getParameter("password"));
        SqlUserUtils.addUser(userInfo);
        resp.sendRedirect("/");
    }

    private void hashUserInfo(String pword) {
        byte[] salt = null;
        try {
            salt = HashUtils.getSalt();
        } catch (NoSuchAlgorithmException exc) {
            System.out.println(exc.getMessage());
        }
        String hashedpwrd = HashUtils.get_SHA_256_SecurePassword(pword, salt);
        try {
            userInfo.put("salt", new String(salt, "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        userInfo.put("hashedpwd", hashedpwrd);
    }
}
