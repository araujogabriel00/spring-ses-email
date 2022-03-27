package com.ga.email.controllers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.ga.email.dtos.EmailDTO;
import com.ga.email.models.EmailModel;
import com.ga.email.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<EmailModel> sendEmail(@RequestBody @Valid EmailDTO emailDTO) throws MessagingException {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);
        return new ResponseEntity<EmailModel>(emailModel, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmailModel>> getAll(){
        return ResponseEntity.ok().body(emailService.getAll());
    }




}
