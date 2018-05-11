package com.codecool.shop.controller;

import com.codecool.shop.dao.interfaces.ProductCategoryDao;
import com.codecool.shop.dao.interfaces.ProductDao;
import com.codecool.shop.dao.interfaces.SupplierDao;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJdbc;
import com.codecool.shop.dao.implementation.jdbc.ProductDaoJdbc;
import com.codecool.shop.dao.implementation.jdbc.SupplierDaoJdbc;
import com.codecool.shop.dao.implementation.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.memory.ProductDaoMem;
import com.codecool.shop.dao.implementation.memory.SupplierDaoMem;
import com.codecool.shop.model.OrderJdbc;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.util.NetworkUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.codecool.shop.model.Order;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/"})
public class Index extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDao productDataStore;
        ProductCategoryDao productCategoryDataStore;
        SupplierDao supplierDataStore;
        if (System.getenv("datastore").equals("jdbc")) {
            productDataStore = ProductDaoJdbc.getInstance();
            productCategoryDataStore = ProductCategoryDaoJdbc.getInstance();
            supplierDataStore = SupplierDaoJdbc.getInstance();
        } else {
            productDataStore = ProductDaoMem.getInstance();
            productCategoryDataStore = ProductCategoryDaoMem.getInstance();
            supplierDataStore = SupplierDaoMem.getInstance();
        }

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        List<ProductCategory> categories = productCategoryDataStore.getAll();
        List<Supplier> suppliers = supplierDataStore.getAll();

        context.setVariable("categories", categories);
        String category = req.getParameter("category");
        String supplier = req.getParameter("supplier");
        HttpSession session = NetworkUtils.getHTTPSession(req);
        if (session.getAttribute("loggedIn") == null) {
            session.setAttribute("loggedIn", "false");
        }
        Order order = (Order) session.getAttribute("Order");
        if (category == null && supplier == null) {
            context.setVariable("products", productDataStore.getAll());
        } else if (category == null) {
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(supplier)));
        } else if (supplier == null) {
            ProductCategory productCategory = productCategoryDataStore.find(category);
            context.setVariable("products", productDataStore.getBy(productCategory));
        } else {
            context.setVariable("products", productDataStore.getBy(supplierDataStore.find(supplier), productCategoryDataStore.find(category)));
        }

        context.setVariable("shoppingCart", order);
        context.setVariable("categories", categories);
        context.setVariable("suppliers", suppliers);
        context.setVariable("recipient", "World");

        engine.process("product/index.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session = NetworkUtils.getHTTPSession(req);
        String process = req.getParameter("process");
        String stringId = (String) session.getAttribute("userid");
        String json = "";
        if (stringId != null) {
        int userId = Integer.parseInt(stringId);
            switch (process) {
                case "add":
                    if (OrderJdbc.findQuantity(id) == 0) {
                        OrderJdbc.add(id, userId, 1);
                    } else {
                        OrderJdbc.update(OrderJdbc.findQuantity(id) + 1, id);
                    }
                    break;
                case "remove":
                    OrderJdbc.removeItemFromCart(id, userId);
                    json = OrderJdbc.getShoppingCart(userId).toJSONString();
                    break;
                case "increment":
                    OrderJdbc.updateIncrement(id, userId);
                    json = OrderJdbc.getShoppingCart(userId).toJSONString();
                    break;
                case "decrement":
                    OrderJdbc.updateDecrement(id, userId);
                    json = OrderJdbc.getShoppingCart(userId).toJSONString();
                    break;
                case "openCart":
                    json = OrderJdbc.getShoppingCart(userId).toJSONString();
                    break;
            }
        } else {
            json = "Please log in to add to cart!";
        }
        resp.getWriter().write(json);

    }
}
