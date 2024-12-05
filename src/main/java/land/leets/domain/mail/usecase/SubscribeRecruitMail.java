package land.leets.domain.mail.usecase;

import land.leets.domain.mail.controller.dto.RecruitMailRequest;
import land.leets.domain.mail.domain.RecruitMail;
import land.leets.domain.mail.domain.repository.RecruitMailRepository;
import land.leets.domain.mail.exception.MailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeRecruitMail {

    private final RecruitMailRepository recruitMailRepository;

    public void execute(RecruitMailRequest request) {
        if (recruitMailRepository.existsByEmail(request.email())) {
            throw new MailAlreadyExistsException();
        }
        RecruitMail recruitMail = new RecruitMail(request.email());

        recruitMailRepository.save(recruitMail);
    }
}
