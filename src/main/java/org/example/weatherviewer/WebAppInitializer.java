package org.example.weatherviewer;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.example.weatherviewer.common.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

    static {
        try {
            Dotenv dotenv = Dotenv.load();

            String apiKey = dotenv.get("OPENWEATHER_API_KEY");
            String dbUsername = dotenv.get("DB_USERNAME");
            String dbPassword = dotenv.get("DB_PASSWORD");

            if (apiKey != null) {
                System.setProperty("OPENWEATHER_API_KEY", apiKey);
                System.out.println("OPENWEATHER_API_KEY loaded: " + apiKey);
            }

            if (dbUsername != null) {
                System.setProperty("DB_USERNAME", dbUsername);
                System.out.println("DB_USERNAME loaded: " + dbUsername);
            }

            if (dbPassword != null) {
                System.setProperty("DB_PASSWORD", dbPassword);
                System.out.println("DB_PASSWORD loaded: ");
            }

        } catch (Exception e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }

    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic registration = container.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");



        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        container.addFilter("hiddenHttpMethodFilter", hiddenHttpMethodFilter)
                .addMappingForServletNames(null, true, "dispatcher");
    }
}