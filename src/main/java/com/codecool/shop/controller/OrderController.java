package com.codecool.shop.controller;

import com.codecool.shop.dao.jdbcImplementation.ProductDaoJDBC;
import com.codecool.shop.dao.memImplementation.OrderDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private static OrderDaoMem orderList = OrderDaoMem.getInstance();

    private static void updateSession(Request req, Order currentOrder) {
        req.session().attribute("orderQuantity", currentOrder.getOrderQuantity());
        req.session().attribute("orderPrice", currentOrder.getOrderPrice());
        logger.info("Successfully updated the current session with quantity of {} and with orderprice of {}", currentOrder.getOrderQuantity(), currentOrder.getOrderPrice());
    }

    private static LineItem returnLineItemFromReq(Request req) {
        String productIdStr = req.queryParams("prodId");
        String productQuantityStr = req.queryParams("quantity");
        int productQuantityInt = Integer.parseInt(productQuantityStr);
        int productIdInt = Integer.parseInt(productIdStr);
        logger.info("Returned lineItem (product id: {}) with quantity of {}", productIdStr, productQuantityStr);
        return new LineItem(ProductDaoJDBC.getInstance().find(productIdInt), productQuantityInt);
    }

    private static Order findCurrentOrder(Request req) {
        Order currentOrder = new Order();
        if (!req.session().attributes().contains("orderId")) {
            orderList.add(currentOrder);
            req.session().attribute("orderId", currentOrder.getId());
            logger.info("New currentOrder is added to orderList with id of {}",Integer.toString(req.session().attribute( "orderId")));
        } else {
            int orderId = req.session().attribute("orderId");
            currentOrder = orderList.find(orderId);
            logger.info("Find currentOrder in orderList with id of {}", Integer.toString(orderId));
        }
        return currentOrder;
    }

    public static JSONObject addToCart(Request req, Response res) {
        LineItem selectedItem = returnLineItemFromReq(req);
        Order currentOrder = findCurrentOrder(req);
        currentOrder.addLineItem(selectedItem);
        updateSession(req, currentOrder);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("numOfLineItems", currentOrder.getOrderQuantity());
        res.type("application/json");
        logger.debug("Selected item: {} is added to cart with id of {}", selectedItem.getProduct().getName(), selectedItem.getProduct().getId());
        return jsonObj;
    }
}
