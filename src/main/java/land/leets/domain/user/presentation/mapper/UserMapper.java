package land.leets.domain.user.presentation.mapper;

import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDetailsResponse mappingUserToDto(User user, SubmitStatus submitStatus);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", ignore = true)
    void updateUserFromDto(@MappingTarget User user, ApplicationRequest request);
}
