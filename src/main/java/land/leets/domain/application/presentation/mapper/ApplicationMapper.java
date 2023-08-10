package land.leets.domain.application.presentation.mapper;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateApplicationFromDto(@MappingTarget Application application, ApplicationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ApplicationResponse mappingApplicationToDto(Application application);
}