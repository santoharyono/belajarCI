/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.controller;

import com.mitrais.demo.belajarCI.dao.ProductDao;
import com.mitrais.demo.belajarCI.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Santo Haryono Weli
 * @version $Id: ProductController.java, v 0.1 2018-11-01 9:19 Santo Haryono Weli Exp $$
 */

@RestController
@RequestMapping("/api/product")
@Transactional(readOnly = true)
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody @Valid Product product, UriComponentsBuilder uriBuilder) {
        productDao.save(product);
        URI location = uriBuilder.path("/api/product/{id}")
                .buildAndExpand(product.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
