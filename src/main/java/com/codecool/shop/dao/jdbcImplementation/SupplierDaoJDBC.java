package com.codecool.shop.dao.jdbcImplementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>SupplierDaoJDBC Class</h1>
 * It models Supplier Table.
 */
public class SupplierDaoJDBC extends JDBCAbstract implements SupplierDao {

    private static SupplierDaoJDBC instance = null;

    private SupplierDaoJDBC() {
    }

    /**
     * It returns SupplierDaoJDBC instance (and creates if doesn't exists).
     * @return SupplierDaoJDBC
     */
    public static SupplierDaoJDBC getInstance() {
        if (instance == null) {
            instance = new SupplierDaoJDBC();
        }
        return instance;
    }

    /**
     * It adds Supplier with parameters (name, description) to the table.
     * @param supplier
     */
    public void add(Supplier supplier) {
        String insertIntoTable = "INSERT INTO Supplier (name, description) VALUES (?,?);";
        try {
            // Adding record to DB
            preparedStatement = dbConnection.prepareStatement(insertIntoTable);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.executeUpdate();

            // Get the ID of the most recent record and update our supplier
            String findSupplier = "SELECT id FROM Supplier ORDER BY id DESC LIMIT 1;";
            preparedStatement = dbConnection.prepareStatement(findSupplier);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                supplier.setId(result.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * It finds all Suppliers with a specific Id.
     * @param id
     * @return Supplier
     */
    public Supplier find(int id) {
        String query = "SELECT * FROM Supplier WHERE id = ?;";
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                Supplier supplier = new Supplier(
                        result.getString("name"),
                        result.getString("description"));
                supplier.setId(result.getInt("id"));
                return supplier;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void remove(int id) {
        remove(id, "Supplier");
    }

    /**
     * It returns all Suppliers.
     * @return List<Supplier>
     */
    public List<Supplier> getAll() {
        String query = "SELECT * FROM supplier";
        List<Supplier> supplierList = new ArrayList<>();
        try {
            preparedStatement = dbConnection.prepareStatement(query,
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY,
                    ResultSet.CLOSE_CURSORS_AT_COMMIT);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                Supplier supplier = new Supplier(
                        result.getString("name"),
                        result.getString("description"));
                supplier.setId(result.getInt("id"));
                supplierList.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    /**
     * It removes all Suppliers.
     */
    public void removeAll() {
        try {
            String removeRecords = "TRUNCATE Supplier CASCADE;";
            preparedStatement = dbConnection.prepareStatement(removeRecords);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
