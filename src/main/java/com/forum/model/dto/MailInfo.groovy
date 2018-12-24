package com.forum.model.dto

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(value = "prototype")
class MailInfo implements Serializable {
    private String subject
    private String text
    private String sender
    private String receiver
    private boolean useHTTP
    private String attachName
    private File file

    String getSender() {
        return sender
    }

    void setSender(String sender) {
        this.sender = sender
    }

    String getAttachName() {
        return attachName
    }

    void setAttachName(String attachName) {
        this.attachName = attachName
    }

    String getSubject() {
        return subject
    }

    void setSubject(String subject) {
        this.subject = subject
    }

    String getText() {
        return text
    }

    void setText(String text) {
        this.text = text
    }

    String getReceiver() {
        return receiver
    }

    void setReceiver(String receiver) {
        this.receiver = receiver
    }

    boolean getUseHTTP() {
        return useHTTP
    }

    void setUseHTTP(boolean useHTTP) {
        this.useHTTP = useHTTP
    }

    File getFile() {
        return file
    }

    void setFile(File file) {
        this.file = file
    }
}
