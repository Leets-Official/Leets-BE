package land.leets.domain.portfolio.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class PortfolioNotFoundException : ServiceException(ErrorCode.PORTFOLIO_NOT_FOUND)
