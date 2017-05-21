package com.codecool.shop.dao.jdbcImplementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductDaoJDBC extends JDBCAbstract implements ProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJDBC.class);
    private static ProductDaoJDBC instance = null;

    private SupplierDaoJDBC supplierDaoJdbc = SupplierDaoJDBC.getInstance();
    private ProductCategoryDaoJDBC productCategoryDaoJdbc = ProductCategoryDaoJDBC.getInstance();

    private ProductDaoJDBC() {
    }

    public static ProductDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

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
            logger.trace("Successfully added a product to the table");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

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
            logger.info("Successfully found a product (id: {})", Integer.toString(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        remove(id, "Product");
    }

    public void removeAll() {
        try {
            String removeRecords = "TRUNCATE Product CASCADE;";
            preparedStatement = dbConnection.prepareStatement(removeRecords);
            preparedStatement.executeUpdate();
            logger.trace("Successfully remove a product from the table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
            logger.trace("Successfully returned all products from the table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

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
            logger.info("Successfully returned all products by supplier (id: {}) from the table", Integer.toString(supplier.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

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
            logger.info("Successfully returned all products by category (id: {}) from the table", Integer.toString(productCategory.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}


