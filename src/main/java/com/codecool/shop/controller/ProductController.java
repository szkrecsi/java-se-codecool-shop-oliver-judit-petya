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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static SupplierDao productSupplierDataStore = SupplierDaoJDBC.getInstance();
    private static ProductDao productDataStore = ProductDaoJDBC.getInstance();
    private static ProductCategoryDao productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();

    public static ModelAndView renderProducts(Request req, Response res) {
        req.session(true);

        Map indexRenderParams = paramFiller(req);
        indexRenderParams.put("products", productDataStore.getAll());
        logger.info("All products are loaded to index page");
        return new ModelAndView(indexRenderParams, "product/index");
    }

    public static ModelAndView renderProductsbyCategory(Request req, Response res, int categoryID) {
        Map categoryRenderParams = paramFiller(req);
        categoryRenderParams.put("products", productDataStore.getBy(productCategoryDataStore.find(categoryID)));
        logger.info("Products filtered by category");
        return new ModelAndView(categoryRenderParams, "product/index");
    }

    public static ModelAndView renderProductsbySupplier(Request req, Response res, int supplierID) {
        Map supRenderParams = paramFiller(req);
        supRenderParams.put("products", productDataStore.getBy(productSupplierDataStore.find(supplierID)));
        logger.info("Products filtered by supplier");
        return new ModelAndView(supRenderParams, "product/index");
    }

    public static Map paramFiller(Request req) {
        Map params = new HashMap<>();
        params.put("orderQuantity", req.session().attribute("orderQuantity"));
        params.put("categories", productCategoryDataStore.getAll());
        params.put("suppliers", productSupplierDataStore.getAll());
        logger.trace("params filled with all data");
        return params;
    }
}
