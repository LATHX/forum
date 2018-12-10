package com.forum

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage

@RestController
class HI {

    @Autowired
    private JavaMailSender javaMailSender;
    @RequestMapping("/hi")
    String hi() {
        return 'Hello World'
    }

    @RequestMapping('send')
    send(){
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("llshadowsock@foxmail.com");
            helper.setTo("1971594765@qq.com");
            helper.setSubject("标题：傻楚楚");

            StringBuffer sb = new StringBuffer();
            sb.append("<h1>大标题傻楚楚</h1>")
                    .append("<p style='color:#F00'>蠢楚楚</p>")
                    .append("<p style='text-align:right'>傻</p>");
            helper.setText(sb.toString(), true);
//            FileSystemResource fileSystemResource=new FileSystemResource(new File("D:\76678.pdf"))
//            helper.addAttachment("电子发票",fileSystemResource);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
