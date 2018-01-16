package com.webill.app.controller;

import com.webill.app.util.MD5;
import com.webill.core.Constant;
import com.webill.core.model.User;
import com.webill.core.service.IUserService;
import com.webill.framework.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by david on 16/10/9.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/loginPost", method = RequestMethod.POST,produces={MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public Object postLogin(String username, String password) {
        boolean flag = true;
        String encodePassword = MD5.encodeByMd5AndSalt(password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, encodePassword);
        try {
            Authentication authentication = authenticationManager.authenticate(authRequest); // 调用loadUserByUsername
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
            User quser = new User();
            quser.setUsername(username);
            User user = userService.selectOne(quser);
            session.setAttribute(Constant.LOGIN_USER, user);

        } catch (Exception e) {
            logger.error("login user:"+username,e);
            flag = false;
        }
        return flag ? renderSuccess("添加失败") : renderError("添加失败");
    }
}
