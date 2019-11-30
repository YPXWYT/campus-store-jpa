package com.tna.campus_store.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tna.campus_store.utils.RedisUtils;

public class Interceptor implements HandlerInterceptor{
	@Autowired
	private RedisUtils redisUtils;
    // 在业务处理器处理请求之前被调用  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{  
    	String token = request.getHeader("token");
    	if(token==null||token.equals("")) {
            returnJson(response,"{\"code\":2001,\"msg\":\"未授权...\"}");
    		 return false; 
    	}
    	if(redisUtils.hasKey(token)) {    		
    		 return true;  
    	}else {
            returnJson(response,"{\"code\":2002,\"msg\":\"授权已过期...!\"}");
    		 return false;  
    	}    
    }  
    
    /*返回客户端数据*/
    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
 
        } catch (IOException e) {
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    // 在业务处理器处理请求完成之后，生成视图之前执行  
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)  
      throws Exception{  
    }  
    // 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源  
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)  
      throws Exception{  
    } 
}
