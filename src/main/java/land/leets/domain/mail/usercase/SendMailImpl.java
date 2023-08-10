package land.leets.domain.mail.usercase;

import land.leets.domain.application.type.Result;
import land.leets.global.mail.MailProvider;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RequiredArgsConstructor
@Service
@Transactional
public class SendMailImpl implements SendMail {

    private final MailProvider mailProvider;
    private static final String EXAMPLE_LINK_TEMPLATE = "mail.html";
    private final TemplateEngine templateEngine;

    @Override
    public void execute(String to, String name, Result result) {

        Context context = getContext(name);
        String message = templateEngine.process(EXAMPLE_LINK_TEMPLATE, context);

        MailDto mailDto = null;
        if (result == Result.FAIL_PAPER || result == Result.PASS_PAPER) {
            mailDto = MailDto.builder()
                    .to(new String[]{to})
                    .title("[Leets] 서류 결과 안내 메일입니다.")
                    .body(message)
                    .build();
        }
        assert mailDto != null;
        mailProvider.sendEmail(mailDto);
    }

    private Context getContext(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return context;
    }
}
