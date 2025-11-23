package com.vidhan.FarmchainX.config;

import com.vidhan.FarmchainX.entity.ProductStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToProductStatusConverter());
    }

    private static class StringToProductStatusConverter implements Converter<String, ProductStatus> {
        @Override
        public ProductStatus convert(String source) {
            try {
                return ProductStatus.valueOf(source.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid ProductStatus: " + source);
            }
        }
    }
}
