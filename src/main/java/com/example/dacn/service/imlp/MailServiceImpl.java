package com.example.dacn.service.imlp;

import com.example.dacn.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Override
    @Async
    public void sendSimpleMail(String[] to, String subject, UUID id,
                               String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom(emailFrom);
        mimeMessage.setSubject(subject);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, "UTF-8");
        helper.setText(htmlMail(id, token), true);
        helper.setTo(to);
        mailSender.send(helper.getMimeMessage());
    }


    private String htmlMail(UUID id, String token) {
        return MessageFormat.format("""
                <!DOCTYPE html>
                       <html lang="en">
                       <head>
                           <meta charset="UTF-8">
                           <meta name="viewport" content="width=device-width, initial-scale=1">
                           <title>Xác thực tài khoản</title>
                           <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css" rel="stylesheet"
                                 integrity="sha384-LN+7fdVzj6u52u30Kp6M/trliBMCMKTyK833zpbD+pXdCLuTusPj697FH4R/5mcr" crossorigin="anonymous">
                       </head>
                       <body>
                       <main class="container">
                           <h1 class="shadow-lg bg-body-tertiary rounded text-center" style="width: fit-content; margin: 0 auto;">Xác thực tài
                               khoản</h1>
                           <div class="card bg-tertiary" style="width: 18rem; margin: 0 auto;">
                               <div class="card-body">
                                   <p class="card-title">Nhấn vào mã để xác thực: <a href="http://localhost:8080/api/v1/auth/xac-thuc?id={0}&token={1}"  style="text-decoration: none">{1}</a></p>
                               </div>
                           </div>
                       </main>
                       </body>
                       </html>
                """, id, token);
    }
}
