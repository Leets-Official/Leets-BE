package land.leets.domain.mail.usecase;

import land.leets.domain.application.type.ApplicationStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static land.leets.TestFixture.application;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SendPaperMailImplTest {

    @Autowired
    private SendFinalMailImpl sendFinalMail;

    @DisplayName("병렬 처리로 메일을 전송합니다.")
    @Test
    @Disabled
    void send100Mail() {
        sendFinalMail.execute(ApplicationStatus.PASS, List.of(
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application, application, application, application, application,
                application, application, application, application
        ));
    }

    @DisplayName("병렬 처리로 5개 메일을 전송합니다.")
    @Test
    @Disabled
    void send5Mail() {
        sendFinalMail.execute(ApplicationStatus.PASS, List.of(
                application, application, application, application, application
        ));
    }

    @DisplayName("병렬 처리로 50개 메일을 전송합니다.")
    @Test
    @Disabled
    void send50Mail() {
        sendFinalMail.execute(ApplicationStatus.PASS, List.of(
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application,
                application, application, application, application, application
        ));
    }
}
