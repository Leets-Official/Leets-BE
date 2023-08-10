package land.leets.domain.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.application.domain.Application;
import land.leets.domain.application.usecase.GetApplicationDetails;
import land.leets.domain.mail.usercase.SendMail;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final SendMail sendMail;
    private final GetApplicationDetails getApplicationDetails;

    @Operation(summary = "결과 메일 전송", description = "지원 결과를 메일로 전송합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}")
    public boolean sendMail(@PathVariable Long id) {
        Application application = getApplicationDetails.execute(id);

        sendMail.execute(application.getUser().getEmail(), application.getName(), application.getResult());
        return true;
    }
}