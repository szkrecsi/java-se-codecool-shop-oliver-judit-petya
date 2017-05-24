package com.codecool.shop.dao.jdbcImplementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.model.ProductCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>ProductCategoryDaoJDBC Class</h1>
 * It models ProductCategory Table.
 */
public class ProductCategoryDaoJDBC extends JDBCAbstract implements ProductCategoryDao {

    private static ProductCategoryDaoJDBC instance = null;

    private ProductCategoryDaoJDBC() {
    }

    /**
     * It returns ProductCategoryDaoJDBC instance (and creates if doesn't exists).
     * @return ProductCategoryDaoJDBC
     */
    public static ProductCategoryDaoJDBC getInstance() {
        if (instance == null) {
            instance = new ProductCategoryDaoJDBC();
        }
        return instance;
    }

    /**
     * It adds productCategory with parameters (name, description, department) to the table.
     * @param productCategory
     */
    public void add(ProductCategory productCategory) {
        String insertIntoTable = "INSERT INTO productcategory (name, description, department) VALUES (?,?,?);";

        try {
            preparedStatement = dbConnection.prepareStatement(insertIntoTable);
            preparedStatement.setString(1, productCategory.getName());
            preparedStatement.setString(2, productCategory.getDescription());
            preparedStatement.setString(3, productCategory.getDepartment());
            preparedStatement.executeUpdate();

            // Get the ID of the most recent record and update our supplier
            String findProductCategory = "SELECT id FROM ProductCategory ORDER BY id DESC LIMIT 1;";
            preparedStatement = dbConnection.prepareStatement(findProductCategory);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                productCategory.setId(result.getInt("id"));
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    /**
     * It finds all ProductCategories with a specific Id.
     * @param id
     * @return ProductCategory
     */
    public ProductCategory find(int id) {
        String query = "SELECT * FROM ProductCategory WHERE id = ?;";

        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                ProductCategory productCategory = new ProductCategory(
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("department"));
                productCategory.setId(result.getInt("id"));
                return productCategory;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        remove(id, "ProductCategory");
    }

    /**
     * It returns all ProductCategories.
     * @return List<ProductCategory>
     */
    public List<ProductCategory> getAll() {
        String query = "SELECT * FROM ProductCategory";
        List<ProductCategory> productCategoryList = new ArrayList<>();

        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                ProductCategory productCategory = new ProductCategory(
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("department"));
                productCategory.setId(result.getInt("id"));
                productCategoryList.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productCategoryList;
    }

    /**
     * It removes all ProductCategories.
     */
    public void removeAll() {
        String removeRecords = "TRUNCATE productcategory CASCADE;";

        try {
            preparedStatement = dbConnection.prepareStatement(removeRecords);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


