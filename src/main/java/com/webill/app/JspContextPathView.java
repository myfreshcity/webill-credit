package com.webill.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.servlet.view.JstlView;

import java.io.IOException;
import java.util.Properties;

/**
 * 将base路径写入到request中，供jsp视图使用（目标根路径）
 */
public class JspContextPathView extends JstlView {
    private  static Properties props = null;
    static {
        try {
            props = PropertiesLoaderUtils.loadAllProperties("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void exposeHelpers(HttpServletRequest request) throws Exception {
        request.setAttribute("base", request.getContextPath());
        request.setAttribute("staticBase",props.get("DOMAIN_STATIC_URL"));
        request.setAttribute("fileBase",props.get("FILE_SHOW_URL"));
        super.exposeHelpers(request);
    }

}
