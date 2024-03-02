package land.leets.domain.interview.usecase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.exception.ApplicationNotFoundException;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest;
import land.leets.domain.interview.presentation.dto.req.InterviewRequest;
import land.leets.domain.interview.type.HasInterview;
import land.leets.domain.mail.exception.PatchRequestFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Arrays;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateInterviewImpl implements CreateInterview {
    private final Environment environment;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    @Value("${target.url.dev}")
    private String TARGET_URI_DEV;

    @Value("${target.url.prod}")
    private String TARGET_URI_PROD;

    private final WebClient webClient;

    @Override
    public void execute(UUID uid, HasInterview hasInterview) {
        InterviewAttendanceRequest requestBody = new InterviewAttendanceRequest(uid, hasInterview);
        boolean isProd = Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod"));
        try {
            webClient.patch()
                    .uri(isProd ? TARGET_URI_PROD : TARGET_URI_DEV)
                    .headers(h -> h.setContentType(MediaType.APPLICATION_JSON))
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException ex) {
            throw new PatchRequestFailException();
        }
    }

    @Override
    public Interview execute(InterviewRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(ApplicationNotFoundException::new);
        Interview interview = Interview.builder()
                .application(application)
                .fixedInterviewDate(request.getFixedInterviewDate())
                .place(request.getPlace())
                .build();
        return interviewRepository.save(interview);
    }
}
