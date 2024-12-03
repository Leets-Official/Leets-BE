package land.leets.domain.mail.usecase;

import land.leets.domain.mail.domain.Mail;
import land.leets.domain.mail.domain.repository.RecruitMailRepository;
import land.leets.domain.mail.service.MailManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class SendRecruitMail {

    private static final String MAIL_TITLE = "[Leets] 5기 모집 시작 안내 메일입니다.";
    private static final String GENERATION_FIELD = "generation";
    private static final String TEMPLATE = "Recruit.html";

    private final RecruitMailRepository recruitMailRepository;
    private final TemplateEngine templateEngine;
    private final MailManager mailManager;

    public void execute() {
        List<Mail> mails = recruitMailRepository.findAll().stream()
                .map(recruitMail -> {
                    Context context = makeContext();
                    String message = templateEngine.process(TEMPLATE, context);
                    return new Mail(MAIL_TITLE, recruitMail.getEmail(), message);
                })
                .toList();

        mailManager.sendEmails(mails);
    }

    private Context makeContext() {
        Context context = new Context();
        context.setVariable(GENERATION_FIELD, 5);
        return context;
    }
}
