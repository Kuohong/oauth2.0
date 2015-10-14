package org.power4j.oauth2.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.power4j.oauth2.service.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cute on 2015/10/13.
 */
@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping("index")
    public String index() {
        return "index";
    }

    @RequestMapping("unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }

    /*
     * Logout
     */
    //    @RequestMapping("logout")
    //    public String logout() {
    //        final Subject subject = SecurityUtils.getSubject();
    //        LOG.debug("{} is logout", subject.getPrincipal());
    //        subject.logout();
    //        return "redirect:/";
    //    }

    /*
     * Go login page
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        final LoginVo loginDto = new LoginVo();
        //TODO: Just testing
        loginDto.setUsername("test");

        model.addAttribute("formDto", loginDto);
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute("formDto") LoginVo formDto, BindingResult errors) {

        UsernamePasswordToken token = formDto.token();
        token.setRememberMe(false);

        try {
            SecurityUtils.getSubject().login(token);
        } catch (Exception e) {
            LOG.debug("Error authenticating.", e);
            errors.rejectValue("username", null, "The username or password was not correct.");
            return "login";
        }

        return "redirect:index";
    }


}
