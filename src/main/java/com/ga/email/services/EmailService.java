package com.ga.email.services;

import com.ga.email.enums.StatusEmail;
import com.ga.email.models.EmailModel;
import com.ga.email.repositories.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final EmailRepository emailRepository;

    private final JavaMailSender javaMailSender;

    //Amazon Simple service(SES)
    public void sendEmail(EmailModel emailModel) throws MessagingException {

        emailModel.setSendDateEmail(LocalDateTime.now());

        try {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setTo(emailModel.getEmailTo());
        mimeMessageHelper.setFrom(emailModel.getEmailFrom());
        mimeMessageHelper.setSubject(emailModel.getSubject());
        mimeMessageHelper.setText(emailModel.getText());

        javaMailSender.send(mimeMessage);
        emailModel.setStatusEmail(StatusEmail.SENT);
        }catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }finally {
            emailRepository.save(emailModel);
        }
    }


    public List<EmailModel> getAll() {
        return emailRepository.findAll();
    }
}
