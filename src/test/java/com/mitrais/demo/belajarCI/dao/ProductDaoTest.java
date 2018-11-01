/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.dao;

import com.mitrais.demo.belajarCI.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Santo Haryono Weli
 * @version $Id: ProductDaoTest.java, v 0.1 2018-11-01 9:04 Santo Haryono Weli Exp $$
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@Sql(scripts = { "/sql/delete-data.sql", "/sql/sample-product.sql" })
public class ProductDaoTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testSave() {
        Product product = new Product();
        product.setCode("T-001");
        product.setName("Test Product 001");
        product.setPrice(BigDecimal.valueOf(100));

        Assert.assertNull(product.getId());
        productDao.save(product);
        Assert.assertNotNull(product.getId());
    }

    @Test
    public void testFindById() {
        Optional<Product> productOptional = productDao.findById("abc123");

        Assert.assertNotNull(productOptional.get());
        Assert.assertEquals("P-001", productOptional.get().getCode());
        Assert.assertEquals("Product 001", productOptional.get().getName());
        Assert.assertEquals(BigDecimal.valueOf(10000.01), productOptional.get().getPrice());

        Assert.assertEquals(productDao.findById("notexist"), Optional.empty());

    }
}
