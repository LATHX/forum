package com.forum.service.impl

import com.forum.global.Constant
import com.forum.model.dto.MailInfo
import com.forum.service.MailService
import com.forum.utils.CommonUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

import javax.mail.internet.MimeMessage

@Service
class MailServiceImpl implements MailService{
    @Autowired
    private JavaMailSender javaMailSender
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private void sendText(MailInfo mailInfo) {
        MimeMessage message = null;
        message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailInfo.getSender());
        helper.setTo(mailInfo.getReceiver());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getText(), mailInfo.getUseHTTP());
        javaMailSender.send(message);
    }

    private void sendTextWithAttach(MailInfo mailInfo) {
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

    @Override
    void sendMail(MailInfo mailInfo) {
        if(Constant.ENABLE_SEND_MAIL == 'true'){
            if(CommonUtil.isEmpty(mailInfo.getFile())){
                sendText(mailInfo)
            }else{
                sendTextWithAttach(mailInfo)
            }
        }else{
            logger.info('Email disabled')
        }

    }
}
