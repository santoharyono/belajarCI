/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.mitrais.demo.belajarCI.dao.ProductDao;
import com.mitrais.demo.belajarCI.entity.Product;
import com.mitrais.demo.belajarCI.exception.DataNotFoundException;
import com.mitrais.demo.belajarCI.exception.ParamIllegalException;

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
    public ResponseEntity<Product> create(@RequestBody @Valid Product product,
                                          UriComponentsBuilder uriBuilder) {
        productDao.save(product);
        URI location = uriBuilder.path("/api/product/{id}").buildAndExpand(product.getId()).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<Product> findAll(Pageable page) {
        return productDao.findAll(page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product findById(@PathVariable("id") Product product) {
        if (product == null)
            throw new ParamIllegalException("Parameter cannot be null");

        Optional<Product> productOptional = productDao.findById(product.getId());

        if (productOptional.isPresent())
            return productOptional.get();
        else
            return new Product();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = false)
    public void update(@PathVariable("id") String id, @RequestBody @Valid Product product) {
        if (!productDao.existsById(id))
            throw new DataNotFoundException("No data with specified id");

        product.setId(id);
        productDao.save(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = false)
    public void delete(@PathVariable("id") String id) {
        if (!productDao.existsById(id))
            throw new DataNotFoundException("No data with specified id");

        Optional<Product> productOptional = productDao.findById(id);
        if (productOptional.isPresent())
            productDao.delete(productOptional.get());
    }
}
