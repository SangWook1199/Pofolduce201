package org.kosa._musketeers.config;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	public static final String REPO_PATH_PORTFOLIO = System.getProperty("user.home")
            + File.separator + "pofolduce201"
            + File.separator + "uploads"
            + File.separator + "portfolio";
	
	public static final String REPO_PATH_PROFILE = System.getProperty("user.home")
			+ File.separator + "pofolduce201"
			+ File.separator + "uploads"
			+ File.separator + "profile";
	public static final String REPO_PATH_COMPANY = System.getProperty("user.home")
			+ File.separator + "pofolduce201"
			+ File.separator + "uploads"
			+ File.separator + "certification";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/portfolio/**") 
                .addResourceLocations("file:" + REPO_PATH_PORTFOLIO + File.separator);
        
        registry.addResourceHandler("/uploads/profile/**") 
                .addResourceLocations("file:" + REPO_PATH_PROFILE + File.separator);
        
        registry.addResourceHandler("/uploads/certification/**") 
                .addResourceLocations("file:" + REPO_PATH_COMPANY + File.separator);
    }
}
