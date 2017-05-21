package com.codecool.shop.model;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Supplier extends BaseModel {

    private static final Logger logger = LoggerFactory.getLogger(Supplier.class);
    private ArrayList<Product> products;

    public Supplier(String name) {
        super(name);
        this.products = new ArrayList<>();
    }

    public Supplier(String name, String description) {
        super(name, description);
        this.products = new ArrayList<>();
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        logger.trace("Supplier's products changed");
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Supplier)) return false;
        Supplier otherSupplier = (Supplier) other;
        if (this.getId() == otherSupplier.getId() &&
                this.getName().equals(otherSupplier.getName())
                ) {
            return true;
        }
        return false;
    }
}