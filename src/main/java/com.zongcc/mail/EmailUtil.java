package com.zongcc.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chunchengzong on 2016-11-11.
 */
@Component("simpleMail")
public class EmailUtil {
    @Resource
    private JavaMailSender mailSender;
    @Resource
    private SimpleMailMessage simpleMailMessage;

    /**
     * @方法名: sendMail
     * @参数名：@param subject  邮件主题
     * @参数名：@param content 邮件主题内容
     * @参数名：@param to         收件人Email地址
     * @描述语: 发送邮件
     */
    public void sendMail(String subject, String content, String to) {

        simpleMailMessage.setSubject(subject); //设置邮件主题
        simpleMailMessage.setTo(to);             //设定收件人
        simpleMailMessage.setText(content);  //设置邮件主题内容
        mailSender.send(simpleMailMessage); //发送邮件
        System.out.println("成功发送普通邮件!");

    }

    public void sendMimeMessage() {
        //附件文件集合
        final List files = new ArrayList();
        MimeMessagePreparator mimeMail = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress("dxrczcc@163.com"));
                mimeMessage.setFrom(new InternetAddress("dxrczcc@163.com"));
                mimeMessage.setSubject("Spring发送带附件的邮件", "gb2312");

                Multipart mp = new MimeMultipart();

                //向Multipart添加正文
                MimeBodyPart content = new MimeBodyPart();
                content.setText("内含spring邮件发送的例子，请查收!");

                //向MimeMessage添加（Multipart代表正文）
                mp.addBodyPart(content);
                files.add("D:\\test.txt");

                //向Multipart添加附件
                Iterator it = files.iterator();
                while(it.hasNext()) {
                    MimeBodyPart attachFile = new MimeBodyPart();
                    String filename = it.next().toString();
                    FileDataSource fds = new FileDataSource(filename);
                    attachFile.setDataHandler(new DataHandler(fds));
                    attachFile.setFileName(fds.getName());
                    mp.addBodyPart(attachFile);
                }

                files.clear();

                //向Multipart添加MimeMessage
                mimeMessage.setContent(mp);
                mimeMessage.setSentDate(new Date());
            }
        };

        //发送带附件的邮件
        mailSender.send(mimeMail);

        System.out.println("成功发送带附件邮件!");
    }
}
