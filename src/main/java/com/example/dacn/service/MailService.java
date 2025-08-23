package com.example.dacn.service;

import jakarta.mail.MessagingException;

import java.util.UUID;

public interface MailService {


    void sendSimpleMail(String[] to, String subject, UUID id, String token) throws MessagingException;
}
