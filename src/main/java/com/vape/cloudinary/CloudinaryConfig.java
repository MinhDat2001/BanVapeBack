package com.vape.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class CloudinaryConfig {
    Cloudinary cloudinary;
    @Value("${cloud_name}")
    private String CLOUD_NAME;
    @Value("${api_key}")
    private String API_KEY;
    @Value("${api_secret}")
    private String API_SECRET;
    @Bean
    public void configCloudinary(){
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CLOUD_NAME,
                "api_key", API_KEY,
                "api_secret", API_SECRET));
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }
}