package land.leets.domain.application.presentation.mapper;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse;
import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.application.presentation.dto.ApplicationResponse;
import land.leets.domain.application.presentation.dto.StatusRequest;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ApplicationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateApplicationFromDto(@MappingTarget Application application, ApplicationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateApplicationFromDto(@MappingTarget Application application, StatusRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ApplicationResponse mappingToDto(Application application, InterviewResponse interview, String phone);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "application.id")
    ApplicationDetailsResponse mappingDetailsToDto(Application application, InterviewDetailsResponse interview, String phone);
}