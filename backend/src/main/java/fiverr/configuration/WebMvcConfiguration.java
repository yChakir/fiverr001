package fiverr.configuration;

import fiverr.converter.UserToProfileConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:/static/image/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserToProfileConverter());
    }
}
