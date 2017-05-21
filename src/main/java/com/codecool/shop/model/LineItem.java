package com.codecool.shop.model;

public class LineItem {

    public Product product;
    protected int quantity;
    protected float totalPrice;

    public LineItem(Product product) {
        this.product = product;
        this.quantity = 1;
        this.totalPrice = product.getDefaultPrice();
    }

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = quantity * product.getDefaultPrice();
    }

    public static boolean equals(LineItem lineItemOne, LineItem lineItemTwo) {
        if (lineItemOne.getProduct() == lineItemTwo.getProduct() &&
                lineItemOne.getTotalPrice() == lineItemTwo.getTotalPrice()
            ) {
            return true;
        }
        return false;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public Product getProduct() {
        return product;
    }
}
