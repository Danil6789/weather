package org.example.weatherviewer;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.example.weatherviewer.config.AppConfig;
import org.example.weatherviewer.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    static {
        try {
            Dotenv dotenv = Dotenv.load();
            String apiKey = dotenv.get("OPENWEATHER_API_KEY");
            System.out.println("OPENWEATHER_API_KEY loaded: " + apiKey);
            System.setProperty("OPENWEATHER_API_KEY", apiKey);
        } catch (Exception e) {
            System.err.println("Error my love ApiKey: " + e.getMessage());
        }
    }


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



        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        container.addFilter("hiddenHttpMethodFilter", hiddenHttpMethodFilter)
                .addMappingForServletNames(null, true, "dispatcher");
    }
}
