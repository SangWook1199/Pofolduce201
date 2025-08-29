package org.kosa._musketeers.config;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	private static final String REPO_PATH_PORTFOLIO = System.getProperty("user.home")
            + File.separator + "pofolduce201"
            + File.separator + "uploads"
            + File.separator + "portfolio";
	
	private static final String REPO_PATH_PROFILE = System.getProperty("user.home")
			+ File.separator + "pofolduce201"
			+ File.separator + "uploads"
			+ File.separator + "profile";
	private static final String REPO_PATH_COMPANY = System.getProperty("user.home")
			+ File.separator + "pofolduce201"
			+ File.separator + "uploads"
			+ File.separator + "certification";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/portfolio/**") 
                .addResourceLocations("file:///" + REPO_PATH_PORTFOLIO + "/");
        
        registry.addResourceHandler("/uploads/profile/**") 
        .addResourceLocations("file:///" + REPO_PATH_PROFILE + "/");
        
        registry.addResourceHandler("/uploads/certification/**") 
        .addResourceLocations("file:///" + REPO_PATH_COMPANY + "/");
    }
}
