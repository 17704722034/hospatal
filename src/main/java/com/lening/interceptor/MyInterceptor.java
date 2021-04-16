package com.lening.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MyInterceptor implements HandlerInterceptor {
    //使用getset方法注入
    private List exceptUrls;

    public List getExceptUrls() {
        return exceptUrls;
    }

    public void setExceptUrls(List exceptUrls) {
        this.exceptUrls = exceptUrls;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String uri = request.getRequestURI();
        //包含的函数
        if (exceptUrls.contains(uri)){
            //直接放行
            return true;
        }else {
            //这里是需要过滤的,首先判断有没有登录，要是没有登录 重新登录
            Object ub = request.getSession().getAttribute("ub");
            if (ub==null){
                //没有登录  直接去登录
                request.getRequestDispatcher("/index.html").forward(request, response);
            }else {
                return true;
            }
        }
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
