package com.github.stan256.bblaccount.service;

import com.github.stan256.bblaccount.model.MailModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import net.sargue.mailgun.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MailService {

    private final Configuration templateConfiguration;

    private final net.sargue.mailgun.Configuration mailConfig = new net.sargue.mailgun.Configuration(
            "sandboxd65fe7d77cab480ca72ce23ae79c6fd0.mailgun.org",
            "79407001ac0420ee5eae1cace7b6f96a-95f6ca46-930e60ce",
            "boobl trips"
    );

    @Value("${app.velocity.templates.location}")
    private String basePackagePath;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${app.token.password.reset.duration}")
    private Long expiration;

    @Autowired
    public MailService(Configuration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }

    @PostConstruct
    public void post() {
    }

    public void sendEmailVerification(String emailVerificationUrl, String to) throws IOException, TemplateException {
        MailModel mailModel = new MailModel();
        mailModel.setSubject("Email Verification");
        mailModel.setTo(to);
        mailModel.setFrom(mailFrom);
        mailModel.getModel().put("userName", to);
        mailModel.getModel().put("userEmailTokenVerificationLink", emailVerificationUrl);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("email-verification.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());
        mailModel.setContent(mailContent);
        send(mailModel);
    }

    public void sendResetLink(String resetPasswordLink, String to) throws IOException, TemplateException {
        long expirationInMinutes = TimeUnit.MILLISECONDS.toMinutes(expiration);
        MailModel mailModel = new MailModel();
        mailModel.setSubject("Password Reset Link [Team CEP]");
        mailModel.setTo(to);
        mailModel.setFrom(mailFrom);
        mailModel.getModel().put("userName", to);
        mailModel.getModel().put("userResetPasswordLink", resetPasswordLink);
        mailModel.getModel().put("expirationTime", Long.toString(expirationInMinutes));

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("reset-link.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());
        mailModel.setContent(mailContent);
        send(mailModel);
    }

    public void sendAccountChangeEmail(String action, String actionStatus, String to) throws IOException, TemplateException {
        MailModel mailModel = new MailModel();
        mailModel.setSubject("Account Status Change [Team CEP]");
        mailModel.setTo(to);
        mailModel.setFrom(mailFrom);
        mailModel.getModel().put("userName", to);
        mailModel.getModel().put("action", action);
        mailModel.getModel().put("actionStatus", actionStatus);

        templateConfiguration.setClassForTemplateLoading(getClass(), basePackagePath);
        Template template = templateConfiguration.getTemplate("account-activity-change.ftl");
        String mailContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());
        mailModel.setContent(mailContent);
        send(mailModel);
    }

    public void send(MailModel mailModel) {
        Mail.using(mailConfig)
                .to(mailModel.getTo())
                .subject(mailModel.getSubject())
                .html(mailModel.getContent())
                .from(mailModel.getFrom())
                .build()
                .send();
    }

}
