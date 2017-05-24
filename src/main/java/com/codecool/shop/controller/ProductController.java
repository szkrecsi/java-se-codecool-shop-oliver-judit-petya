package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.jdbcImplementation.ProductCategoryDaoJDBC;
import com.codecool.shop.dao.jdbcImplementation.ProductDaoJDBC;
import com.codecool.shop.dao.jdbcImplementation.SupplierDaoJDBC;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
* <h1>ProductController Class</h1>
* Queries related to the Product class are located here.
*/
public class ProductController {
    private static SupplierDao productSupplierDataStore = SupplierDaoJDBC.getInstance();
    private static ProductDao productDataStore = ProductDaoJDBC.getInstance();
    private static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();

    /**
     * It returns (~ loads) all products on index page.
     * @param req
     * @param res
     * @return ModelAndView
     */
    public static ModelAndView renderProducts(Request req, Response res) {
        req.session(true);

        Map indexRenderParams = paramFiller(req);
        indexRenderParams.put("products", productDataStore.getAll());
        return new ModelAndView(indexRenderParams, "product/index");
    }

    /**
     * It returns (~ loads) all products by category.
     * @param req
     * @param res
     * @param categoryID
     * @return ModelAndView
     */
    public static ModelAndView renderProductsbyCategory(Request req, Response res, int categoryID) {
        Map categoryRenderParams = paramFiller(req);
        categoryRenderParams.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryID)));
        return new ModelAndView(categoryRenderParams, "product/index");
    }

    /**
     * It returns (~ loads) all products by supplier.
     * @param req
     * @param res
     * @param supplierID
     * @return ModelAndView
     */
    public static ModelAndView renderProductsbySupplier(Request req, Response res, int supplierID) {
        Map supRenderParams = paramFiller(req);
        supRenderParams.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierID)));
        return new ModelAndView(supRenderParams, "product/index");
    }

    /**
     * It adds and returns currentOrder's /cart/ summarized quantity and categories, suppliers to HashMap parameters.
     * @param req
     * @return Map
     */
    public static Map paramFiller(Request req) {
        Map params = new HashMap<>();
        params.put("orderQuantity", req.session().attribute("orderQuantity"));
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", productSupplierDataStore.getAll());
        return params;
    }
}
