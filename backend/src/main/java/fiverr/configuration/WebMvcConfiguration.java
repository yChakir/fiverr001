package fiverr.configuration;

import fiverr.converter.ContactRequestToContact;
import fiverr.converter.UserToProfileConverter;
import fiverr.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final HandlerInterceptor jwtAuthorizationFilter;

    public WebMvcConfiguration(HandlerInterceptor jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserToProfileConverter());
        registry.addConverter(new ContactRequestToContact());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthorizationFilter);
    }
}
