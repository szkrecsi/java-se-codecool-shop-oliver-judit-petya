package com.codecool.shop.dao.jdbcImplementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ProductDaoJDBC Class</h1>
 * It models Product Table.
 */
public class ProductDaoJDBC extends JDBCAbstract implements ProductDao {

    private static ProductDaoJDBC instance = null;

    private SupplierDaoJDBC supplierDaoJdbc = SupplierDaoJDBC.getInstance();
    private ProductCategoryDaoJDBC productCategoryDaoJdbc = ProductCategoryDaoJDBC.getInstance();

    private ProductDaoJDBC() {
    }

    /**
     * It returns ProductDaoJDBC instance (and creates if doesn't exists).
     * @return ProductDaoJDBC
     */
    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    /**
     * It adds product with parameters (name, description, currency, default_price, supplier_id, product_category_id) to the table.
     * @param product
     */
    public void add(Product product) {
        String insertIntoTable = "INSERT INTO product (name, description, currency, default_price, supplier_id, product_category_id) VALUES (?,?,?,?,?,?);";
        try {
            // Add record to DB
            preparedStatement = dbConnection.prepareStatement(insertIntoTable);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setString(3, product.getDefaultCurrency().toString());
            preparedStatement.setFloat(4, product.getDefaultPrice());
            preparedStatement.setInt(5, product.getSupplier().getId());
            preparedStatement.setInt(6, product.getProductCategory().getId());
            preparedStatement.executeUpdate();

            // Get the ID of the most recent record and update our supplier
            String findProduct = "SELECT id FROM Product ORDER BY id DESC LIMIT 1;";
            preparedStatement = dbConnection.prepareStatement(findProduct);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                product.setId(result.getInt("id"));
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * It finds all Products with a specific Id.
     * @param id
     * @return Product
     */
    public Product find(int id) {
        String query = "SELECT * FROM Product WHERE id = ?;";
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                Product product = new Product(
                        result.getString("name"),
                        result.getFloat("default_price"),
                        result.getString("currency"),
                        result.getString("description"),
                        productCategoryDaoJdbc.find(result.getInt("product_category_id")),
                        supplierDaoJdbc.find(result.getInt("supplier_id"))
                );
                product.setId(result.getInt("id"));
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        remove(id, "Product");
    }

    /**
     * It removes all Products.
     */
    public void removeAll() {
        try {
            String removeRecords = "TRUNCATE Product CASCADE;";
            preparedStatement = dbConnection.prepareStatement(removeRecords);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * It returns all Products.
     * @return List<Product>
     */
    public List<Product> getAll() {
        String query = "SELECT * FROM Product;";
        List<Product> productList = new ArrayList<>();

        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Product product = new Product(
                        result.getString("name"),
                        result.getFloat("default_price"),
                        result.getString("currency"),
                        result.getString("description"),
                        productCategoryDaoJdbc.find(result.getInt("product_category_id")),
                        supplierDaoJdbc.find(result.getInt("supplier_id"))
                );
                product.setId(result.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * It finds all Products by Supplier.
     * @param supplier
     * @return List<Product>
     */
    public List<Product> getBy(Supplier supplier) {
        String query = "SELECT * FROM Product WHERE supplier_id = ?;";
        List<Product> productList = new ArrayList<>();

        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setInt(1, supplier.getId());
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Product product = new Product(
                        result.getString("name"),
                        result.getFloat("default_price"),
                        result.getString("currency"),
                        result.getString("description"),
                        productCategoryDaoJdbc.find(result.getInt("product_category_id")),
                        supplierDaoJdbc.find(result.getInt("supplier_id"))
                );
                product.setId(result.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * It finds all Products by ProductCategory.
     * @param productCategory
     * @return List<Product>
     */
    public List<Product> getBy(ProductCategory productCategory) {
        String query = "SELECT * FROM Product WHERE product_category_id = ?;";
        List<Product> productList = new ArrayList<>();

        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setInt(1, productCategory.getId());
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Product product = new Product(
                        result.getString("name"),
                        result.getFloat("default_price"),
                        result.getString("currency"),
                        result.getString("description"),
                        productCategoryDaoJdbc.find(result.getInt("product_category_id")),
                        supplierDaoJdbc.find(result.getInt("supplier_id"))
                );
                product.setId(result.getInt("id"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}


