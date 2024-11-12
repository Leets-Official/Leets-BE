package land.leets.domain.mail.service;

import java.util.List;
import java.util.Objects;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.function.ThrowingSupplier;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import land.leets.domain.mail.domain.Mail;
import land.leets.domain.mail.exception.MailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailFactory {

	private final JavaMailSender javaMailSender;

	public MimeMessage createMail(Mail mail) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(mail.to());
			mimeMessageHelper.setSubject(mail.title());
			mimeMessageHelper.setText(mail.body(), true);
			return mimeMessage;
		} catch (MessagingException e) {
			throw new MailException();
		}
	}

	@Async
	public void sendMails(List<MimeMessage> batch) {
		try {
			javaMailSender.send(batch.toArray(new MimeMessage[0]));
		} catch (MailSendException e) {
			MimeMessage[] failedMessages = e.getFailedMessages().keySet()
				.stream()
				.map(object -> (MimeMessage)object)
				.toArray(MimeMessage[]::new);
			retry(failedMessages);
		}
	}

	private void retry(MimeMessage[] failedMessages) {
		try {
			javaMailSender.send(failedMessages);
		} catch (MailSendException e) {
			e.getFailedMessages().keySet()
				.stream()
				.map(failure -> (MimeMessage)failure)
				.map(mimeMessage -> apply(mimeMessage::getAllRecipients))
				.filter(Objects::nonNull)
				.forEach(address -> log.error("fail to send mail. address = {}", address));
		}
	}

	private Address apply(ThrowingSupplier<Address[]> function) {
		try {
			return function.get()[0];
		} catch (Exception e) {
			log.error("fail to load failed address");
			return null;
		}
	}
}
