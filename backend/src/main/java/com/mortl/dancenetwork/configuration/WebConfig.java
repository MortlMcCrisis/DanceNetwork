package com.mortl.dancenetwork.configuration;

import java.io.File;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{

  // for local flutter image upload
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry)
  {
    String uploadDirPath = System.getProperty("user.dir") + File.separator + "uploads";
    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:" + uploadDirPath + File.separator);
  }
}