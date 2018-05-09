package com.codecool.shop.controller;


import com.codecool.shop.util.HashUtils;
import com.codecool.shop.util.NetworkUtils;
import com.codecool.shop.util.SqlUserUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;


@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("user");
        HashMap<String, String> userInfo = SqlUserUtils.getUser(email);
        if (loginAuth(req.getParameter("password"), userInfo.get("salt"), userInfo.get("password"))) {
            HttpSession session = NetworkUtils.getHTTPSession(req);
            session.setAttribute("username", userInfo.get("username"));
            session.setAttribute("userid", userInfo.get("id"));
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("True");
        }
    }


    private boolean loginAuth(String pwrd, String userSalt, String hashFromDb) {
        boolean authLogin = false;
        try {
            authLogin = hashFromDb.equals(HashUtils.get_SHA_256_SecurePassword(pwrd, userSalt.getBytes("ISO-8859-1")));
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        return authLogin;
    }
}
