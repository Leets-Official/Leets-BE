package land.leets.global.cron;

import land.leets.domain.mail.usecase.SendMail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendMailCron {
    private final SendMail sendMail;

    @Scheduled(cron = "0 0 10 19 3 ?")
    public void sendPaperMail() {
        sendMail.execute("paper");

        log.info("Send paper result mail successfully.");
    }

    @Scheduled(cron = "0 40 14 27 3 ?")
    public void sendFinalMail() {
        sendMail.execute("final");

        log.info("Send final result mail successfully.");
    }

    //     @Scheduled(cron = "0 28 23 1 9 ?")
    public void sendPlusMail() {
        sendMail.execute("plus");

        log.info("Send final result mail successfully.");
    }
}
