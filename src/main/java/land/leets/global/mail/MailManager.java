package land.leets.global.mail;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailManager {

	private static final int BATCH_SIZE = 40;

	private final MailFactory mailFactory;

	public void sendEmails(List<MailDto> mailDtos) {
		List<List<MimeMessage>> batchedMails = createChunkedMails(mailDtos);

		batchedMails.parallelStream()
			.forEach(mailFactory::sendMails);
	}

	private List<List<MimeMessage>> createChunkedMails(List<MailDto> mailDtos) {
		List<MimeMessage> messages = mailDtos.parallelStream()
			.map(mailFactory::createMail)
			.toList();
		return partitionMessages(messages);
	}

	private List<List<MimeMessage>> partitionMessages(List<MimeMessage> messages) {
		return IntStream.range(0, (messages.size() + BATCH_SIZE - 1) / BATCH_SIZE)
			.mapToObj(i -> messages.subList(i * BATCH_SIZE, Math.min((i + 1) * BATCH_SIZE, messages.size())))
			.toList();
	}
}
