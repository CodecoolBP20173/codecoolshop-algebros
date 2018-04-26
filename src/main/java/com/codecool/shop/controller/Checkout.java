package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.model.CheckoutProcess;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.Product;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/checkout"})
public class Checkout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        HttpSession session;
        if (req.getSession(false) == null  ){
            session = req.getSession(true);
            session.setAttribute("Order", new Order());
        } else {
            session = req.getSession();
        }
        Order order = (Order) session.getAttribute("Order");
        context.setVariable("shoppingCart", order.getCartItems());
        double sumPrice = totalPrice(order);
        context.setVariable("sumPrice", sumPrice);
        engine.process("product/checkout.html", context, resp.getWriter());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Map customerInfo = req.getParameterMap();
        CheckoutProcess checkoutProcess = new CheckoutProcess(customerInfo);
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("Order");
        order.checkout(checkoutProcess);
        resp.sendRedirect("/payment");
    }


    public double totalPrice(Order order) {
        double sumPrice = 0;
        List<Product> orderCartItems = order.getItemList();
        for (Product item: orderCartItems) {
            sumPrice += (item.getDefaultPrice() * order.getOrderQuantity().get(item.getId()));
        }
        return sumPrice;
    }
}
