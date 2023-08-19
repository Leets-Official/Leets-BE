package land.leets.global.cron;

import land.leets.domain.mail.usercase.SendMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendMailCron {
    private final SendMail sendMail;

//    @Scheduled(cron = "0 0 10 4 9 ?")
    @Scheduled(cron = "30 28 0 20 8 ?")
    public void sendPaperMail() {
        sendMail.execute("paper");

        log.info("Send paper result mail successfully.");
    }

    @Scheduled(cron = "0 0 18 11 9 ?")
    public void sendFinalMail() {
        sendMail.execute("final");

        log.info("Send final result mail successfully.");
    }
}
