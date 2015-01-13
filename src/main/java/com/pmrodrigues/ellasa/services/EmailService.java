package com.pmrodrigues.ellasa.services;

import com.pmrodrigues.ellasa.exceptions.EnderecoEmailInvalidoException;
import com.pmrodrigues.ellasa.exceptions.ErrorProcessamentoDeTemplateException;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;

import static java.lang.String.format;

@Service
public class EmailService {

    @Resource(name = "mailSender")
    private JavaMailSender sender;

    private MimeMessage message;

    @Resource(name = "velocityEngine")
    private VelocityEngine velocityEngine;

    public EmailService from(final String email) {

        try {
            createMimeMessage();
            message.setFrom(new InternetAddress(email));
            return this;
        } catch (MessagingException e) {
            throw new EnderecoEmailInvalidoException(format(
                    "O endereço %s é inválido", email));
        }
    }

    public EmailService to(final String email) {
        try {
            createMimeMessage();
            message.setRecipient(RecipientType.TO, new InternetAddress(email));
            return this;
        } catch (MessagingException e) {
            throw new EnderecoEmailInvalidoException(format(
                    "O endereço %s é inválido", email));
        }
    }

    public EmailService cc(final String email) {
        try {
            createMimeMessage();
            message.setRecipient(RecipientType.CC, new InternetAddress(email));
            return this;
        } catch (MessagingException e) {
            throw new EnderecoEmailInvalidoException(format(
                    "O endereço %s é inválido", email));
        }
    }

    public EmailService subject(final String subject) {
        try {
            createMimeMessage();
            message.setSubject(subject);
            return this;
        } catch (Exception e) {
            return this;
        }
    }

    public EmailService template(final String template,
                                 Map<String, Object> parameters) {

        try {
            createMimeMessage();
            final String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, template, "UTF-8", parameters);

            message.setContent(text, "text/html; charset=utf-8");

            return this;
        } catch (MessagingException e) {
            throw new ErrorProcessamentoDeTemplateException(e.getMessage(), e);
        }
    }

    public void send() {
        this.sender.send(this.message);
    }

    private void createMimeMessage() {
        if (message == null) {
            message = sender.createMimeMessage();
        }
    }
}
