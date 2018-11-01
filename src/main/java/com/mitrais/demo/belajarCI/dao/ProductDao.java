/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.dao;

import com.mitrais.demo.belajarCI.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Santo Haryono Weli
 * @version $Id: ProductDao.java, v 0.1 2018-11-01 8:50 Santo Haryono Weli Exp $$
 */
public interface ProductDao extends PagingAndSortingRepository<Product, String> {
}
