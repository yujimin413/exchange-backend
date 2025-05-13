package ShinHoDeung.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<HeaderFilter> loggingFilter() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HeaderFilter());
        registrationBean.addUrlPatterns("/*");  // 필터가 적용될 URL 패턴 설정
        return registrationBean;
    }
}