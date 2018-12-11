package com.forum.service.impl

import com.forum.model.dto.MailInfo
import com.forum.service.MailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

import javax.mail.internet.MimeMessage
@Service
class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    void sendText(MailInfo mailInfo) {
        MimeMessage message = null;
        message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailInfo.getSender());
        helper.setTo(mailInfo.getReceiver());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getText(), mailInfo.getUseHTTP());
        javaMailSender.send(message);
    }

    @Override
    void sendTextWithAttach(MailInfo mailInfo) {
        MimeMessage message = null;
        message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailInfo.getSender());
        helper.setTo(mailInfo.getReceiver());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getText(), mailInfo.getUseHTTP());
        FileSystemResource fileSystemResource=new FileSystemResource(mailInfo.getFile())
        helper.addAttachment(mailInfo.getAttachName(),fileSystemResource);
        javaMailSender.send(message);
    }
}
