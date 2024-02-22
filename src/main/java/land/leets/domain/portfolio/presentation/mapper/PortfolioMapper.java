package land.leets.domain.portfolio.presentation.mapper;

import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PortfolioMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PortfolioResponse mappingPortfolioToDto(Portfolio portfolio);
}
