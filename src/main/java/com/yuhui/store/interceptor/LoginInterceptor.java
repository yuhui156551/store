package com.yuhui.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 检测全局session对象中是否有uid数据，有则放行，没有则重定向到登陆界面
     * @param request 请求对象
     * @param response
     * @param handler 处理器（url+Controller：映射）
     * @return 返回值为true表示放行
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        Object obj = request.getSession().getAttribute("uid");
        if(obj == null){
            //说明用户未登录，重定向
            response.sendRedirect("/web/login.html");

            return false;
        }
        //放行
        return true;
    }
}
