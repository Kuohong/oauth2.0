package com.power4j.oauth2.rs.web.controller;

import com.power4j.oauth2.rs.service.vo.OpenIDVo;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: org.power4j.oauth2.web.controller.UserController <br>
 *
 * @author Kuo Hong
 * @version 2015-10-14
 */
@Controller
@RequestMapping("user/")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


    /**
     * RESTFUL
     * Return username API
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     */
    @RequiresRoles("User")
    @RequestMapping("openid")
    @ResponseBody
    public OpenIDVo username(HttpServletRequest request, HttpServletResponse response) throws Exception {

        final String clientId = (String) request.getAttribute(OAuth.OAUTH_CLIENT_ID);
        LOG.debug("Current clientId: {}", clientId);

        final String username = request.getUserPrincipal().getName();
        LOG.debug("Current username: {}", username);

        return new OpenIDVo(clientId, username);
    }
}
