package land.leets.global.cron;

import java.util.List;

import org.springframework.stereotype.Component;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.mail.usecase.SendFinalMailImpl;
import land.leets.domain.mail.usecase.SendPaperMailImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailCron {

	private final ApplicationRepository applicationRepository;
	private final SendFinalMailImpl sendFinalMailImpl;
	private final SendPaperMailImpl sendPaperMailImpl;

	//    @Scheduled(cron = "0 0 10 19 3 ?")
	public void sendPaperMail() {
		for (ApplicationStatus status : ApplicationStatus.papers()) {
			List<Application> applications = applicationRepository.findAllByApplicationStatus(status);
			sendPaperMailImpl.execute(status, applications);
		}

		log.info("Send paper result mail successfully.");
	}

	//    @Scheduled(cron = "0 40 14 27 3 ?")
	public void sendFinalMail() {
		for (ApplicationStatus status : ApplicationStatus.finals()) {
			List<Application> applications = applicationRepository.findAllByApplicationStatus(status);
			sendFinalMailImpl.execute(status, applications);
		}

		log.info("Send final result mail successfully.");
	}

	//     @Scheduled(cron = "0 28 23 1 9 ?")
	public void sendPlusMail() {
		// sendMail.execute("plus");

		log.info("Send final result mail successfully.");
	}
}
