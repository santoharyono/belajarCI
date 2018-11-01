/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Santo Haryono Weli
 * @version $Id: Product.java, v 0.1 2018-11-01 8:53 Santo Haryono Weli Exp $$
 */

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 10)
    @Column(nullable = false, unique = true)
    private String code;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private BigDecimal price;
}
