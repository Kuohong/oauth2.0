package org.power4j.framework.context;

import org.power4j.oauth2.utils.BeanProvider;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;

/**
 * Created by cute on 2015/10/13.
 */
public class BeanContextLoaderListener  extends ContextLoaderListener {


    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        WebApplicationContext applicationContext = WebApplicationContextUtils
            .getRequiredWebApplicationContext(event.getServletContext());
        BeanProvider.initialize(applicationContext);
    }
}

