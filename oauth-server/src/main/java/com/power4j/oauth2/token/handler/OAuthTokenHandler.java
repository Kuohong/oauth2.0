/*
 * Copyright (c) 2013 Andaily Information Technology Co. Ltd
 * www.andaily.com
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Andaily Information Technology Co. Ltd ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with Andaily Information Technology Co. Ltd.
 */
package com.power4j.oauth2.token.handler;


import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import com.power4j.oauth2.web.wapper.OAuthTokenxRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 2015/7/3
 *
 * @author Kuo Hong
 */

public interface OAuthTokenHandler {


    boolean support(OAuthTokenxRequest tokenRequest) throws OAuthProblemException;

    void handle(OAuthTokenxRequest tokenRequest, HttpServletResponse response) throws OAuthProblemException, OAuthSystemException;


    List<String> getSupportedGrantTypes();
}
