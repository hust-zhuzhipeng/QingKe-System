package util;
/**
 * 邮件发送的工具类
 */
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service("mailUtil")
public class MailUtil{
	@Autowired()
	@Qualifier("executor")
	private Executor executor;
	private static Logger log = LoggerFactory.getLogger(MailUtil.class);
	@Value("${smtp.host}")
	private String smtphost;
	@Value("${smtp.port}")
	private int port;
	@Value("${smtp.username}")
	private String username;
	@Value("${smtp.password}")
	private String password;
	@Value("${mail.transport.protocol}")
	private String protocol;
	@Value("${mail.smtp.auth}")
	private boolean auth;
	@Value("${mail.smtp.socketFactory.class}")
	private String socketFactoryClass;	
	//邮件发送实例
	private JavaMailSenderImpl javaMailSender;
	@PostConstruct
    public void javaMailSender(){
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(smtphost);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties properties = new Properties();
        properties.setProperty("mail.host", smtphost);
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.smtp.auth", auth+"");
        properties.setProperty("mail.smtp.socketFactory.class", socketFactoryClass);
        properties.setProperty("mail.smtp.port", port+"");
        properties.setProperty("mail.smtp.socketFactory.port", port+"");
        javaMailSender.setJavaMailProperties(properties);
    }
	public void sendRegisterMail(String To,String code){
		sendMail(To,"Register Mail",code);
	}
	//发送邮件
	public void sendMail(String To,String subject,String code){
		Runnable r = new Runnable(){
			@Override
			public void run() {
				directsendMail(To,subject,code);
			}
			
		};
		executor.execute(r);
	}
	
	private void directsendMail(String To,String subject,String code) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");
            helper.setFrom(username);// 设置发件人
            helper.setTo(To);// 设置收件人
            helper.setSubject(subject);// 设置主题
            String content = 
					"<html><head></head><body><h1>这是一封测试邮件,不用点击以下链接</h1><h3><a href='http://localhost:8080/RegisterWeb/ActiveServlet?code="
					+ code + "'>http://localhost:8080/receivemail?code=" + code
					+ "</href></h3></body></html>";
            mailMessage.setContent(content, "text/html;charset=UTF-8");
            //helper.setText("测试代码:【"+code+"】");// 邮件体
            javaMailSender.send(mailMessage);// 发送邮件
            log.info("邮件发送成功...");
        } catch (Exception e) {
            log.error("邮件发送发生异常:" + e.getMessage(), e);
        }
    }


}
