package org.next.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    private static final String ROOT = "/";
    private static final String DISPATCHER = "dispatcher";
    private static final String TRUE = "true";
    private final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        rootContext.register(AppConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(AppConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER, new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(ROOT);
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", TRUE);
    }
}
