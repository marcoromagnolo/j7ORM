/**
 * Copyright 2016 Marco Romagnolo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package j7orm.test.testcase;

import j7orm.DBConnection;
import j7orm.GenericDAO;
import j7orm.GenericDAOImpl;
import j7orm.exception.OrmException;
import j7orm.test.dao.BrandDAO;
import j7orm.test.dao.ProductDAO;
import j7orm.test.dao.impl.BrandDAOImpl;
import j7orm.test.dao.impl.ProductDAOImpl;
import j7orm.test.entity.BrandEntity;
import j7orm.test.entity.CategoryEntity;
import j7orm.test.entity.ProductCategoryEntity;
import j7orm.test.entity.ProductEntity;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class TestDAO {

    private BrandDAO<BrandEntity> brandDAO;
    private ProductDAO<ProductEntity> productDAO;
    private GenericDAO<ProductCategoryEntity> productCategoryDAO;
    private GenericDAO<CategoryEntity> categoryDAO;

    public TestDAO(DBConnection connection) {
        brandDAO = new BrandDAOImpl(connection);
        productDAO = new ProductDAOImpl(connection);
        productCategoryDAO = new GenericDAOImpl<>(ProductCategoryEntity.class, connection);
        categoryDAO = new GenericDAOImpl<>(CategoryEntity.class, connection);
    }

    /**
     * Test Insert
     */
    public void insert() throws OrmException {
        PrintUtil.test("DAO - Insert");

        // Insert Brands
        PrintUtil.action("Inserting brands");

        BrandEntity brand = new BrandEntity();
        brand.setId(1000L);
        brand.setName("Brand");
        brandDAO.insert(brand);
        PrintUtil.result("Inserted brand: " + brand);

        BrandEntity brand1 = new BrandEntity();
        brand1.setId(1001L);
        brand1.setName("Brand1");
        brandDAO.insert(brand1);
        PrintUtil.result("Inserted brand: " + brand1);

        //Insert Categories
        PrintUtil.action("Inserting categories");

        CategoryEntity category = new CategoryEntity();
        category.setId(1000L);
        category.setName("Category name");
        category.setDescription("Category description");
        categoryDAO.insert(category);
        PrintUtil.result("Inserted category: " + category);

        CategoryEntity category1 = new CategoryEntity();
        category1.setId(1001L);
        category1.setName("Category name 1");
        category1.setDescription("Category description 1");
        categoryDAO.insert(category1);
        PrintUtil.result("Inserted category: " + category1);

        CategoryEntity category2 = new CategoryEntity();
        category2.setId(1002L);
        category2.setName("Category name 2");
        category2.setDescription("Category description 2");
        categoryDAO.insert(category2);
        PrintUtil.result("Inserted category: " + category2);

        //Insert Products
        PrintUtil.action("Inserting products");

        ProductEntity product = new ProductEntity();
        product.setId(1000L);
        product.setBrandId(brand.getId());
        product.setBlocked(false);
        product.setCreateDate(new Date());
        product.setName("Product name");
        product.setDescription("Product description");
        productDAO.insert(product);
        PrintUtil.result("Inserted product: " + product);

        ProductEntity product1 = new ProductEntity();
        product1.setBrandId(brand.getId());
        product1.setBlocked(false);
        product1.setCreateDate(new Date());
        product1.setName("Product name 1");
        product1.setDescription("Product description 1");
        productDAO.insert(product1);
        PrintUtil.result("Inserted product: " + product1);

        ProductEntity product2 = new ProductEntity();
        product2.setBrandId(brand.getId());
        product2.setBlocked(false);
        product2.setCreateDate(new Date());
        product2.setName("Product name 2");
        product2.setDescription("Product description 2");
        productDAO.insert(product2);
        PrintUtil.result("Inserted product: " + product2);

        //Insert Product's Categories
        PrintUtil.action("Inserting product's categories");

        ProductCategoryEntity prodCat = new ProductCategoryEntity();
        prodCat.setCategoryId(category.getId());
        prodCat.setProductId(product.getId());
        productCategoryDAO.insert(prodCat);
        PrintUtil.result("Inserted product's category: " + prodCat);

        ProductCategoryEntity prodCat1 = new ProductCategoryEntity();
        prodCat1.setCategoryId(category1.getId());
        prodCat1.setProductId(product.getId());
        productCategoryDAO.insert(prodCat1);
        PrintUtil.result("Inserted product's category: " + prodCat1);

        ProductCategoryEntity prodCat2 = new ProductCategoryEntity();
        prodCat2.setCategoryId(category1.getId());
        prodCat2.setProductId(product1.getId());
        productCategoryDAO.insert(prodCat2);
        PrintUtil.result("Inserted product's category: " + prodCat2);

        ProductCategoryEntity prodCat3 = new ProductCategoryEntity();
        prodCat3.setCategoryId(category2.getId());
        prodCat3.setProductId(product2.getId());
        productCategoryDAO.insert(prodCat3);
        PrintUtil.result("Inserted product's category: " + prodCat3);
    }

    /**
     * Find test
     * @throws OrmException
     */
    public void find() throws OrmException {
        PrintUtil.test("DAO - Find");

        PrintUtil.action("Finding brand");
        BrandEntity brand = brandDAO.find(1000L);
        Assert.assertNotNull(brand);
        PrintUtil.result("Found brand:" + brand);

        PrintUtil.action("Finding category");
        CategoryEntity category = categoryDAO.find(1000L);
        Assert.assertNotNull(category);
        PrintUtil.result("Found category:" + category);

        PrintUtil.action("Finding product");
        ProductEntity product = productDAO.find(1000L);
        Assert.assertNotNull(product);
        PrintUtil.result("Found product:" + product);
    }

    /**
     * FindAll test
     * @throws OrmException
     */
    public void findAll() throws OrmException {
        PrintUtil.test("DAO - FindAll");

        PrintUtil.action("Finding all products");
        List<ProductEntity> products = productDAO.findAll();
        Assert.assertNotEquals(new ArrayList<>(), products);
        PrintUtil.result("Founded products:" + products);
    }

    /**
     * Update test
     * @throws OrmException
     */
    public void update() throws OrmException {
        PrintUtil.test("DAO - Update");

        PrintUtil.action("Updating brand");
        BrandEntity brand = brandDAO.find(1000L);
        String newName = "New brand";
        brand.setName(newName);
        brandDAO.update(brand);
        Assert.assertEquals(newName, brand.getName());
        PrintUtil.result("Updated brand: " + brand);

        PrintUtil.action("Updating category");
        CategoryEntity category = categoryDAO.find(1000L);
        Long prevVersion = category.getVersion();
        category.setName("New Category");
        categoryDAO.update(category);
        Assert.assertEquals(category.getVersion().longValue(), prevVersion + 1L);
        PrintUtil.result("Updated category: " + category);

        PrintUtil.action("Updating product");
        ProductEntity product = productDAO.find(1000L);
        product.setId(999L);
        product.setName("New Product");
        int queryDone = productDAO.update(product);
        Assert.assertEquals(queryDone, 1);
        PrintUtil.result("Updated product: " + product);
    }

    /**
     * Delete Test
     * @throws OrmException
     */
    public void delete() throws OrmException {
        PrintUtil.test("DAO - Delete");

        PrintUtil.action("Deleting brand");
        BrandEntity brand = brandDAO.find(1000L);
        Assert.assertNotNull(brand);
        brandDAO.delete(brand);
        BrandEntity delBrand = brandDAO.find(1000L);
        Assert.assertNull(delBrand);
        PrintUtil.result("Deleted brand:" + brand);
    }

    /**
     * Delete All
     * @throws OrmException
     */
    public void deleteAll() throws OrmException {
        PrintUtil.test("DAO - Delete all from tables");
        brandDAO.deleteAll();
        productDAO.deleteAll();
        productCategoryDAO.deleteAll();
        categoryDAO.deleteAll();
    }
}
