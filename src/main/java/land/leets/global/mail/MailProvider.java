package land.leets.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import land.leets.global.mail.dto.MailDto;
import land.leets.global.mail.exception.MailException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MailProvider {

    private static final int THREAD_POOL_SIZE = 4;

    private final JavaMailSender javaMailSender;

    public void sendEmails(List<MailDto> mailDtos) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        mailDtos.forEach(mail ->
                executor.submit(() -> sendEmail(mail))
        );

        executor.shutdown();

        try {
            if (!executor.awaitTermination(180, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private void sendEmail(MailDto mailDto) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(mailDto.to());
            mimeMessageHelper.setSubject(mailDto.title());
            mimeMessageHelper.setText(mailDto.body(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailException();
        }
    }
}
