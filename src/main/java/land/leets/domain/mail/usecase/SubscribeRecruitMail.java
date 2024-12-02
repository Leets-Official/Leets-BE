package land.leets.domain.mail.usecase;

import land.leets.domain.mail.controller.dto.RecruitMailRequest;
import land.leets.domain.mail.domain.RecruitMail;
import land.leets.domain.mail.domain.repository.RecruitMailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SubscribeRecruitMail {

    private final RecruitMailRepository recruitMailRepository;

    public void execute(RecruitMailRequest request) {
        RecruitMail recruitMail = new RecruitMail(request.email());

        recruitMailRepository.save(recruitMail);
    }
}
