package com.lening.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MyFilter implements Filter {
    Set<String> notCheckUrl = new HashSet<String>();
    //过滤器要是需要在web.xml中配置参数读取的话  在init方法中读取
    public void init(FilterConfig filterConfig) throws ServletException {
        String urls = filterConfig.getInitParameter("notCheckUrl");
        for (String url : urls.split(",")) {
            notCheckUrl.add(url.trim());
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //要是这个请求过来的url是不需要过滤的，不管怎样直接放行
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        //包含的函数
        if (notCheckUrl.contains(uri)){
            //直接放行
            chain.doFilter(request, response);
        }else {
            //这里是需要过滤的,首先判断有没有登录，要是没有登录 重新登录
            Object ub = req.getSession().getAttribute("ub");
            if (ub==null){
                //没有登录  直接去登录
                req.getRequestDispatcher("/index.html").forward(request, response);
            }else {
                chain.doFilter(request, response);
            }
        }
    }

    public void destroy() {

    }
}
