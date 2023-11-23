package land.leets.domain.interview.presentation.mapper;

import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest;
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterviewMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInterviewFromDto(@MappingTarget Interview interview, FixedInterviewRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInterviewFromDto(@MappingTarget Interview interview, InterviewAttendanceRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InterviewDetailsResponse mappingDetailsToDto(Interview interview);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InterviewResponse mappingToDto(Interview interview);
}