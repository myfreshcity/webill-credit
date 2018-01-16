package com.webill.framework.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.webill.app.SystemProperty;
import com.webill.app.util.JwtUtil;
import com.webill.framework.common.HzJsonResult;
import com.webill.framework.common.JsonResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by david on 16/10/17.
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected ServletContext application;

    @Autowired
    protected SystemProperty constPro;

    @Autowired
    protected JwtUtil jwtUtil;

    protected <T> Page<T> getPage(int size) {
        int _size = size, _index = 1;
        if (request.getParameter("length") != null) {
            _size = Integer.parseInt(request.getParameter("length"));
        }
        if (request.getParameter("start") != null) {
            int _offset = Integer.parseInt(request.getParameter("start"));
            _index = _offset / _size + 1;
        }
        return new Page<T>(_index, _size);
    }

    /**
     * 渲染失败数据
     *
     * @return result
     */
    protected JsonResult renderError() {
        JsonResult result = new JsonResult();
        result.setSuccess(false);
        result.setStatus("500");
        return result;
    }

    /**
     * 渲染失败数据（带消息）
     *
     * @param msg 需要返回的消息
     * @return result
     */
    protected JsonResult renderError(String msg) {
        JsonResult result = renderError();
        result.setMsg(msg);
        return result;
    }
    
    /**
     * 渲染失败数据（带消息和编码）
     * 
     * @param msg 需要返回的消息
     * @param status 错误编码
     * @return result
     */
    protected JsonResult renderError(String msg,String status) {
        JsonResult result = renderError();
        result.setMsg(msg);
        result.setStatus(status);
        return result;
    }
    
    /**   
     * @Title: renderError   
     * @Description: 渲染失败数据（带消息、编码、数据）
     * @author: WangLongFei  
     * @date: 2017年7月21日 下午4:59:04   
     * @param msg 需要返回的消息
     * @param status 错误编码
     * @param obj	返回的数据
     * @return  
     * @return: JsonResult  
     */
    protected JsonResult renderError(String msg,String status,Object obj) {
    	JsonResult result = renderError();
    	result.setMsg(msg);
    	result.setStatus(status);
    	result.setObj(obj);
    	return result;
    }

    /**
     * 渲染成功数据
     *
     * @return result
     */
    protected JsonResult renderSuccess() {
        JsonResult result = new JsonResult();
        result.setSuccess(true);
        result.setStatus("200");
        return result;
    }

    /**
     * 渲染成功数据（带信息）
     *
     * @param msg 需要返回的信息
     * @return result
     */
    protected JsonResult renderSuccess(String msg) {
        JsonResult result = renderSuccess();
        result.setMsg(msg);
        return result;
    }
    
    /**
     * 渲染成功数据（带信息、编码）
     * 
     * @param msg 需要返回的信息
     * @param status 成功编码
     * @return result
     */
    protected JsonResult renderSuccess(String msg,String status) {
    	JsonResult result = renderSuccess();
    	result.setMsg(msg);
    	result.setStatus(status);
    	return result;
    }
    
    /**   
     * @Title: renderSuccess   
     * @Description: 渲染成功数据（带消息、编码、数据）
     * @author: WangLongFei  
     * @date: 2017年7月21日 下午4:56:25   
     * @param msg 需要返回的信息
     * @param status 成功编码
     * @param obj	返回的数据
     * @return  
     * @return: JsonResult  
     */
    protected JsonResult renderSuccess(String msg,String status,Object obj) {
    	JsonResult result = renderSuccess();
    	result.setMsg(msg);
    	result.setStatus(status);
    	result.setObj(obj);
    	return result;
    }

    /**
     * 渲染成功数据（带数据）
     *
     * @param obj 需要返回的对象
     * @return result
     */
    protected JsonResult renderSuccess(Object obj) {
        JsonResult result = renderSuccess();
        result.setObj(obj);
        return result;
    }
    
    /** 
     * @Title: renderNotifyResult 
     * @Description: 慧择回调时系统响应的数据
     * @author: WangLongFei
     * @date: 2017年11月30日 下午4:49:04 
     * @param state
     * @param failMsg
     * @return
     * @return: HzJsonResult
     */
    protected HzJsonResult renderTrueResult() {
    	HzJsonResult result = new HzJsonResult();
    	result.setState(true);
    	return result;
    }
    /** 
     * @Title: renderNotifyResult 
     * @Description: 慧择回调时系统响应的数据
     * @author: WangLongFei
     * @date: 2017年11月30日 下午4:49:04 
     * @param state
     * @param failMsg
     * @return
     * @return: HzJsonResult
     */
    protected HzJsonResult renderFalseResult(String failMsg) {
    	HzJsonResult result = new HzJsonResult();
    	result.setState(false);
    	result.setFailMsg(failMsg);
        return result;
    }
}
