package fiverr.configuration;

import fiverr.converter.UserToProfileConverter;
import fiverr.filter.JwtAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public WebMvcConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserToProfileConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthorizationFilter);
    }
}
