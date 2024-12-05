package land.leets.domain.mail.controller;

import jakarta.validation.Valid;
import land.leets.domain.mail.controller.dto.RecruitMailRequest;
import land.leets.domain.mail.usecase.SubscribeRecruitMail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.mail.usecase.SendMailCron;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

	private final SendMailCron sendMailCron;
	private final SubscribeRecruitMail subscribeRecruitMail;

	@Operation(summary = "서류 결과 메일 전송", description = "서류 지원 결과를 메일로 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/paper")
	public boolean sendPaperMail() {
		sendMailCron.sendPaperMail();
		return true;
	}

	@Operation(summary = "최종 결과 메일 전송", description = "최종 지원 결과를 메일로 전송합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/final")
	public boolean sendFinalMail() {
		sendMailCron.sendFinalMail();
		return true;
	}

	@Operation(summary = "모집 알림 신청 메일 전송", description = "모집 시작 알림을 메일로 전송합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/recruit")
	public boolean sendRecruitMail() {
		sendMailCron.sendRecruitMail();
		return true;
	}

	@Operation(summary = "모집 알림 신청 메일 등록", description = "모집 시작 알림을 받을 메일을 등록합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/subscribe")
	public boolean subscribeRecruitMail(@Valid @RequestBody RecruitMailRequest request) {
		subscribeRecruitMail.execute(request);
		return true;
	}
}
