package com.codecool.shop.model;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductCategory extends BaseModel {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategory.class);
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
        logger.debug("ProductCategory's department changed to {}", department);
    }

    public ArrayList getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        logger.trace("ProductCategory's products changed");
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

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