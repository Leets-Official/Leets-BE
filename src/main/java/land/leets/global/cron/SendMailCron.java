package land.leets.global.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.mail.usecase.SendFinalMailImpl;
import land.leets.domain.mail.usecase.SendPaperMailImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailCron {

	private final SendFinalMailImpl sendFinalMailImpl;
	private final SendPaperMailImpl sendPaperMailImpl;

	@Scheduled(cron = "0 0 23 8 9 ?")
	public void sendPaperMail() {
		for (ApplicationStatus status : ApplicationStatus.papers()) {
			sendPaperMailImpl.execute(status);
		}

		log.info("Send paper result mail successfully.");
	}

	@Scheduled(cron = "0 30 23 8 9 ?")
	public void sendPassPaperMail() {
		sendPaperMailImpl.execute(ApplicationStatus.PASS_PAPER);

		log.info("Send paper result mail successfully.");
	}

	@Scheduled(cron = "0 0 20 11 9 ?")
	public void sendFinalMail() {
		for (ApplicationStatus status : ApplicationStatus.finals()) {
			sendFinalMailImpl.execute(status);
		}

		log.info("Send final result mail successfully.");
	}

	//     @Scheduled(cron = "0 28 23 1 9 ?")
	public void sendPlusMail() {
		log.info("Send final result mail successfully.");
	}
}

