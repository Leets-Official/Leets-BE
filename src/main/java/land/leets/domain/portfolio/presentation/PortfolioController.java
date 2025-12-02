package land.leets.domain.portfolio.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse;
import land.leets.domain.portfolio.usecase.GetPortfolios;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolios")
public class PortfolioController {

    private final GetPortfolios getPortfolios;

    @Operation(summary = "(비로그인) 포트폴리오 조회", description = "포트폴리오를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<List<PortfoliosResponse>> getAll(@RequestParam(required = false) String generation) {
        return getPortfolios.all(generation);
    }

    @GetMapping("/{portfolioId}")
    public PortfolioResponse get(@PathVariable Long portfolioId) {
        return getPortfolios.one(portfolioId);
    }
}
