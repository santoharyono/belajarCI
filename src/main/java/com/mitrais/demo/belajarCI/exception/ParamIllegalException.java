/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.mitrais.demo.belajarCI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Santo Haryono Weli
 * @version $Id: ParamIllegalException.java, v 0.1 2018-11-01 13:51 Santo Haryono Weli Exp $$
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ParamIllegalException extends RuntimeException {

    public ParamIllegalException(String message) {
        super(message);
    }
}
