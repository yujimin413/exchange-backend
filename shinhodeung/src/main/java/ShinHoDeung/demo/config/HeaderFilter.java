package ShinHoDeung.demo.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class HeaderFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 공통 헤더 추가
        httpResponse.setHeader("Content-Type", "application/json;charset=UTF-8");

        // 다음 필터로 요청 전달
        chain.doFilter(request, response);
    }
}