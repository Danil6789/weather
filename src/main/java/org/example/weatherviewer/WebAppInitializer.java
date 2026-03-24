package org.example.weatherviewer;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.example.weatherviewer.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.register(AppConfig.class);

        DispatcherServlet servlet = new DispatcherServlet((WebApplicationContext) context);

        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", servlet);

        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
