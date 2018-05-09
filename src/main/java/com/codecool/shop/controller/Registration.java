package com.codecool.shop.controller;


import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.util.HashUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {
    private static HashMap<String, String > userInfo;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("user/registration.html", context, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        userInfo = new HashMap<>();
        userInfo.put("name",req.getParameter("Username"));
        userInfo.put("email",req.getParameter("Email"));
        userInfo.put("password", req.getParameter("password"));
        System.out.println(userInfo.put("name",req.getParameter("Username")));
        System.out.println(userInfo.put("email",req.getParameter("Email")));
        System.out.println(userInfo.put("password", req.getParameter("password")));
        hashUserInfo(req.getParameter("password"));
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
    }

    private boolean loginAuth(String pwrd, String userSalt, String hashFromDb) {
        return hashFromDb.equals(HashUtils.get_SHA_256_SecurePassword(pwrd, userSalt.getBytes()));
    }
}
