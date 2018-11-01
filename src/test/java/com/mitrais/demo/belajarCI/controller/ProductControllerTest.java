/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.controller;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mitrais.demo.belajarCI.entity.Product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * @author Santo Haryono Weli
 * @version $Id: ProductControllerTest.java, v 0.1 2018-11-01 9:20 Santo Haryono Weli Exp $$
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/delete-data.sql", "/sql/sample-product.sql"})
public class ProductControllerTest {

    public static final String BASE_URL = "/api/product";

    @Value("${local.server.port}")
    int serverPort;

    @Before
    public void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testSave() throws Exception {
        Product product = new Product();
        product.setCode("PT-002");
        product.setName("Product Test 002");
        product.setPrice(BigDecimal.valueOf(200));

        given()
                .body(product)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/")
                .then()
                .statusCode(201)
                .header("Location", containsString(BASE_URL + "/"))
                .log()
                .headers();

//        test nama tidak diisi
        Product product1 = new Product();
        product1.setCode("PT-002");
        product1.setPrice(BigDecimal.valueOf(200));

        given()
                .body(product1)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/")
                .then()
                .statusCode(400);

//        test code kurang dari 3 karakter
        Product product2 = new Product();
        product2.setCode("P2");
        product2.setName("Product 002");
        product2.setPrice(BigDecimal.valueOf(200));

        given()
                .body(product2)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/")
                .then()
                .statusCode(400);

//        test harga negatif
        Product product3 = new Product();
        product3.setCode("PT-002");
        product3.setName("Product Test 002");
        product3.setPrice(BigDecimal.valueOf(-1000));

        given()
                .body(product3)
                .contentType(ContentType.JSON)
                .when()
                .post(BASE_URL + "/")
                .then()
                .statusCode(400);
    }

    @Test
    public void testFindAll() {
        get(BASE_URL + "/")
                .then()
                .body("totalElements", equalTo(1))
                .body("content.id", hasItem("abc123"));
    }

    @Test
    public void testFindById() {
        get(BASE_URL + "/abc123")
                .then()
                .statusCode(200)
                .body("id", equalTo("abc123"))
                .body("code", equalTo("P-001"));

        get(BASE_URL + "/999")
                .then()
                .statusCode(400);
    }

    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setCode("PX-009");
        product.setName("Product test 009");
        product.setPrice(BigDecimal.valueOf(1000));

        given()
                .body(product)
                .contentType(ContentType.JSON)
                .when()
                .put(BASE_URL + "/abc123")
                .then()
                .statusCode(200);

        get(BASE_URL + "/abc123")
                .then()
                .statusCode(200)
                .body("id", equalTo("abc123"))
                .body("code", equalTo("PX-009"))
                .body("name", equalTo("Product test 009"));
    }

    @Test
    public void testDelete() {
        delete(BASE_URL + "/abc123")
                .then()
                .statusCode(200);

        delete(BASE_URL + "/999")
                .then()
                .statusCode(404);
    }
}
