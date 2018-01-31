package com.webill.core.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.webill.app.SystemProperty;
import com.webill.core.model.EmailBean;
import com.webill.core.service.ISendEmailService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/** 
* @ClassName: SendEmailServiceImpl 
* @Description: 
* @author ZhangYadong
* @date 2017年11月24日 下午3:35:10 
*/
@Service
public class SendEmailServiceImpl implements ISendEmailService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected SystemProperty constPro; 
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	
	@Override
    public void sendEmail(EmailBean eb) {

        Properties props = new Properties();                    		// 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   		// 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", constPro.JM_SEND_SMTPHOST); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            		// 需要请求认证
        // 开启 SSL 安全连接
        props.setProperty("mail.smtp.port", constPro.JM_SEND_PORT);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", constPro.JM_SEND_PORT);
        
        // 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(true); // 设置为debug模式, 可以查看详细的发送 log
       
        // 创建 消息 对象
        MimeMessage message = new MimeMessage(session);

        try {
            // 创建 MimeMessageHelper 对象
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");
            // 设置发件人地址
            mimeMessageHelper.setFrom(new InternetAddress(constPro.JM_SEND_ACCOUNT));
            // 设置收件人地址
            mimeMessageHelper.setTo(new InternetAddress(constPro.JM_RECEIVE_ACCOUNT));
            // 设置主题
            mimeMessageHelper.setSubject(eb.getSubject());
            // 获取模版对象
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(eb.getFtlFile()+".ftl");
    		// 准备的 model data
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("eb", eb);

            // 解析 模版和 数据
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            // 设置 消息内容
            mimeMessageHelper.setText(content, true);
            Transport transport = session.getTransport();
            transport.connect(constPro.JM_SEND_ACCOUNT, constPro.JM_SEND_PASSWORD);
            // 发送消息
            transport.sendMessage(message, message.getAllRecipients());
            // 关闭连接
            transport.close();
        } catch (AddressException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (TemplateException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
	
}
