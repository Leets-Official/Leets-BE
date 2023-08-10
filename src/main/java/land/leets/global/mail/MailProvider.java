package land.leets.global.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailProvider {

    private final JavaMailSender javaMailSender;

    public void sendEmail(MailDto mailDto) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(mailDto.to());
            mimeMessageHelper.setSubject(mailDto.title());
            mimeMessageHelper.setText(mailDto.body(), true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ServiceException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}