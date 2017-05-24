package com.codecool.shop.model;

import java.util.ArrayList;

/**
 *<h1>Supplier Class</h1>
 * It represents the suppliers.
 */
public class Supplier extends BaseModel {
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
    }

    /**
     * It adds a new product to current Supplier instance.
     * @param product
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * It overrides the default toString() method.
     * @return String
     */
    public String toString() {
        return String.format("id: %1$d, " +
                        "name: %2$s, " +
                        "description: %3$s",
                this.id,
                this.name,
                this.description
        );
    }

    /**
     * It overrides the default equals() method.
     * @param other
     * @return boolean
     */
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