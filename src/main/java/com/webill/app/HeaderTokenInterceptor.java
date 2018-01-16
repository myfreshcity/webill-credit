package com.webill.app;

import io.jsonwebtoken.Claims;
import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.webill.app.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class HeaderTokenInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(HeaderTokenInterceptor.class);
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // String  contentPath=httpServletRequest.getContextPath();
        // logger.info("contenxPath:"+contentPath);
    	
        String method = httpServletRequest.getMethod();
        
        String requestURI = httpServletRequest.getRequestURI();
        String tokenStr = httpServletRequest.getParameter("token");
        String token = "";
        String[] ignoreUri ={"/api/user/userLogin",
        		"/api/verifyCode/sendVerifyCode",
        		"/api/user/userRegister",
        		"/api/tOrder/",
        		"/api/product/",
        		"/api/user/userLogout",
        		"/api/user/userLogin"};
        boolean f = false;
        for(String ignore:ignoreUri){
        	f = requestURI.contains(ignore);
        	if(f){
        		break;
        	}
        }
        
        if (requestURI.contains("/api/")&& "POST".equalsIgnoreCase(method)&&!f) {
            token = httpServletRequest.getHeader("token");
            if (token == null && tokenStr == null) {
                logger.info("real token:======================is null");
                String str = "{\"errorCode\":801,\"message\":\"缺少token，无法验证\",\"data\":null}";
                dealErrorReturn(httpServletRequest, httpServletResponse, str);
                return false;
            }
            if (tokenStr != null) {
                token = tokenStr;
            }

            Claims cm = jwtUtil.verifyToken(token);
            if(null != cm){
                token = jwtUtil.updateTokenBase64Code(cm);
                httpServletResponse.setHeader("token", token);
            }else{
                String str = "{\"errorCode\":802,\"message\":\"无效的token\",\"data\":null}";
                dealErrorReturn(httpServletRequest, httpServletResponse, str);
                return false;
            }

            logger.info("real token:==============================" + token);
            logger.info("real ohter:==============================" + httpServletRequest.getHeader("Cookie"));
        }

        /*
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");
        */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    // 检测到没有token，直接返回不验证
    public void dealErrorReturn(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj) {
        String json = (String) obj;
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpServletResponse.setStatus(HttpStatus.SC_FORBIDDEN);
        try {
            writer = httpServletResponse.getWriter();
            writer.print(json);

        } catch (IOException ex) {
            logger.error("response error", ex);
        } finally {
            if (writer != null)
                writer.close();
        }
    }


}
