package land.leets.domain.portfolio.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import land.leets.domain.portfolio.domain.ProjectScope;
import land.leets.domain.portfolio.presentation.dto.PortfolioRequest;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.usecase.CreatePortfolio;
import land.leets.domain.portfolio.usecase.GetPortfolios;
import land.leets.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolios")
public class PortfolioController {

    private final CreatePortfolio createPortfolio;
    private final GetPortfolios getPortfolios;

    @Operation(summary = "(관리자) 포트폴리오 추가", description = "포트폴리오를 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public PortfolioResponse create(@RequestPart PortfolioRequest request,
                                    @RequestPart(required = false) MultipartFile logoImg,
                                    @RequestPart(required = false) MultipartFile coverImg,
                                    @RequestPart(required = false) MultipartFile mainImg) {
        return createPortfolio.execute(request, logoImg, coverImg, mainImg);
    }

    @Operation(summary = "(비로그인) 포트폴리오 조회", description = "포트폴리오를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<List<PortfolioResponse>>  getAll(@RequestParam(required = false) String generation) {
        return getPortfolios.all(generation);
    }

    @GetMapping("/{portfolioId}")
    public PortfolioResponse  get(@PathVariable Long portfolioId) {
        return getPortfolios.one(portfolioId);
    }
}
