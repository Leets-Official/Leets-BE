package land.leets.domain.mail.usercase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.global.mail.MailProvider;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class SendMailImpl implements SendMail {

    private final MailProvider mailProvider;
    private final ApplicationRepository applicationRepository;
    private static final String PASS_PAPER_TEMPLATE = "pass-paper.html";
    private static final String FAIL_PAPER_TEMPLATE = "fail-paper.html";
    private static final String PASS_TEMPLATE = "pass.html";
    private static final String FAIL_TEMPLATE = "fail.html";
    private final TemplateEngine templateEngine;

    @Override
    public void execute() {

        List<Application> applications = applicationRepository.findAll();

        for (Application application : applications) {
            Context context = getContext(application.getName());
            String message = "";

            if (application.getApplicationStatus() == ApplicationStatus.PASS_PAPER) {
                message = templateEngine.process(PASS_PAPER_TEMPLATE, context);
            }
            if (application.getApplicationStatus() == ApplicationStatus.FAIL_PAPER) {
                message = templateEngine.process(FAIL_PAPER_TEMPLATE, context);
            }
            if (application.getApplicationStatus() == ApplicationStatus.PASS) {
                message = templateEngine.process(PASS_TEMPLATE, context);
            }
            if (application.getApplicationStatus() == ApplicationStatus.FAIL) {
                message = templateEngine.process(FAIL_TEMPLATE, context);
            }
            MailDto mailDto = MailDto.builder()
                    .to(new String[]{application.getUser().getEmail()})
                    .title("[Leets] 서류 결과 안내 메일입니다.")
                    .body(message)
                    .build();

            mailProvider.sendEmail(mailDto);
        }
    }

    private Context getContext(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return context;
    }
}
