package land.leets.global.mail;

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
import land.leets.global.mail.dto.MailDto;
import land.leets.global.mail.exception.MailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailFactory {

	private final JavaMailSender javaMailSender;

	public MimeMessage createMail(MailDto mailDto) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			mimeMessageHelper.setTo(mailDto.to());
			mimeMessageHelper.setSubject(mailDto.title());
			mimeMessageHelper.setText(mailDto.body(), true);
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
