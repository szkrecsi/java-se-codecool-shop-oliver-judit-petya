package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Order Class</h1>
 * It represents a shopping cart.
 */
public class Order {

    private List<LineItem> items = new ArrayList<>();
    private float orderPrice;
    private int orderQuantity;
    private int id;

    /**
     * It compares two Orders by Id and their Items.
     * @param orderOne
     * @param orderTwo
     * @return boolean
     */
    public static boolean equals(Order orderOne, Order orderTwo) {
        if (orderOne.getId() == orderTwo.getId() &&
                orderOne.getItems() == orderTwo.getItems()
                ) {
            return true;
        }
        return false;
    }

    /**
     * It finds if the LineItem is in items.
     * If it is, then it increases the quantity and totalPrice.
     * If it isn't, then it adds the LineItem to the items.
     * It calls updateOrderPrice(item) and updateOrderQuantity(item) methods on LineItem.
     * @param item
     */
    public void addLineItem(LineItem item) {
        int counter = 0;
        for (LineItem n : items) {
            if (n.product.equals(item.product)) {
                n.quantity += item.quantity;
                n.totalPrice += item.totalPrice;
                counter += 1;
            }
        }
        if (counter == 0) {
            items.add(item);
        }
        updateOrderPrice(item);
        updateOrderQuantity(item);
    }

    /**
     * It updates OrderPrice with LineItem's price.
     * @param item
     */
    public void updateOrderPrice(LineItem item) {
        this.orderPrice += item.totalPrice;
    }

    /**
     * It updates OrderQuantity with LineItem's quantity.
     * @param item
     */
    public void updateOrderQuantity(LineItem item) {
        this.orderQuantity += item.quantity;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
