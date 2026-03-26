package org.example.weatherviewer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.example.weatherviewer.config.AppConfig;
import org.example.weatherviewer.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();
        servletContext.setParent(rootContext);

        servletContext.register(WebConfig.class);

        DispatcherServlet dispatcherSevlet = new DispatcherServlet(servletContext);
        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", dispatcherSevlet);

        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
