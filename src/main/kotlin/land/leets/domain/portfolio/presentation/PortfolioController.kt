package land.leets.domain.portfolio.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse
import land.leets.domain.portfolio.usecase.GetPortfolios
import land.leets.global.error.ErrorResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/portfolios")
class PortfolioController(
    private val getPortfolios: GetPortfolios
) {

    @Operation(summary = "(비로그인) 포트폴리오 조회", description = "포트폴리오를 조회합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200"),
        ApiResponse(responseCode = "400", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "403", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "500", content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    )
    @GetMapping
    fun getAll(@RequestParam(required = false) generation: String?): List<List<PortfoliosResponse>> {
        return getPortfolios.all(generation)
    }

    @GetMapping("/{portfolioId}")
    fun get(@PathVariable portfolioId: Long): PortfolioResponse {
        return getPortfolios.one(portfolioId)
    }
}
