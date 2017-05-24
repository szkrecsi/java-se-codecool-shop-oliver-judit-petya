package com.codecool.shop.model;

import java.util.ArrayList;

/**
 *<h1>ProductCategory Class</h1>
 * It models the product categories.
 */
public class ProductCategory extends BaseModel {
    private String department;
    private ArrayList<Product> products;

    public ProductCategory(String name, String department, String description) {
        super(name, description);
        this.department = department;
        this.products = new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    /**
     * It adds a new product to current ProductCategory instance.
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
        return String.format(
                "id: %1$d," +
                        "name: %2$s, " +
                        "department: %3$s, " +
                        "description: %4$s",
                this.id,
                this.name,
                this.department,
                this.description);
    }

    /**
     * It overrides the default equals() method.
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof ProductCategory)) return false;
        ProductCategory otherProductCategory = (ProductCategory) other;
        if (this.getId() == otherProductCategory.getId() &&
                this.getName().equals(otherProductCategory.getName())
                ) {
            return true;
        }
        return false;
    }
}