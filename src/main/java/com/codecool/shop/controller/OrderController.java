package com.codecool.shop.controller;

import com.codecool.shop.dao.jdbcImplementation.ProductDaoJDBC;
import com.codecool.shop.dao.memImplementation.OrderDaoMem;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;
import org.json.simple.JSONObject;
import spark.Request;
import spark.Response;

/**
 * <h1>OrderController Class</h1>
 * Queries related to the Order class are located here.
 */
public class OrderController {

    private static OrderDaoMem orderList = OrderDaoMem.getInstance();

    /**
     * It updates the current session with orderQuantity and orderPrice.
     * @param req
     * @param currentOrder
     */
    private static void updateSession(Request req, Order currentOrder) {
        req.session().attribute("orderQuantity", currentOrder.getOrderQuantity());
        req.session().attribute("orderPrice", currentOrder.getOrderPrice());
    }

    /**
     * It creates a new LineItem with input's productId and productQuantity parameters.
     * It calls the ProductDaoJDBC.getInstance().find() method, to get the product by Id.
     * It returns the new LineItem with this product (because of LineItem has Product type instance attribute) and quantity.
     * @param req
     * @return LineItem
     */
    private static LineItem returnLineItemFromReq(Request req) {
        String productIdStr = req.queryParams("prodId");
        String productQuantityStr = req.queryParams("quantity");
        int productQuantityInt = Integer.parseInt(productQuantityStr);
        int productIdInt = Integer.parseInt(productIdStr);
        return new LineItem(ProductDaoJDBC.getInstance().find(productIdInt), productQuantityInt);
    }

    /**
     * It finds if session has OrderId. It creates a new Order instance.
     * If session hasn't OrderId, it adds the new currentOrder to the session and to orderList.
     * If session has OrderId, it overrides the currentOrder with the Order, what it finds in orderList by session's OrderId.
     * It returns the currentOrder.
     * @param req
     * @return Order
     */
    private static Order findCurrentOrder(Request req) {
        Order currentOrder = new Order();
        if (!req.session().attributes().contains("orderId")) {
            orderList.add(currentOrder);
            req.session().attribute("orderId", currentOrder.getId());
        } else {
            int orderId = req.session().attribute("orderId");
            currentOrder = orderList.find(orderId);
        }
        return currentOrder;
    }

    /**
     * It calls the returnLineItemFromReq() and findCurrentOrder() methods, and it gets a new LineItem from returnLineItemFromReq() method.
     * It adds this LineItem to the currentOrder/cart/ (what it finds in findCurrentOrder() method) and it updates session's cart.
     * It returns Cart's Quantity in JSON format.
     * @param req
     * @param res
     * @return JSONObject
     */
    public static JSONObject addToCart(Request req, Response res) {
        LineItem selectedItem = returnLineItemFromReq(req);
        Order currentOrder = findCurrentOrder(req);
        currentOrder.addLineItem(selectedItem);
        updateSession(req, currentOrder);
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("numOfLineItems", currentOrder.getOrderQuantity());
        res.type("application/json");
        return jsonObj;
    }
}
