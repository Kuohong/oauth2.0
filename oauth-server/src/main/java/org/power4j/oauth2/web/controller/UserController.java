package org.power4j.oauth2.web.controller;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.power4j.oauth2.service.vo.UserNameVo;
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
@RequestMapping("/")
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
    @RequestMapping("username")
    @ResponseBody
    public UserNameVo username(HttpServletRequest request, HttpServletResponse response) throws Exception {

        final String clientId = (String) request.getAttribute(OAuth.OAUTH_CLIENT_ID);
        LOG.debug("Current clientId: {}", clientId);

        final String username = request.getUserPrincipal().getName();
        LOG.debug("Current username: {}", username);

        return new UserNameVo(clientId, username);
    }
}
