package land.leets.domain.user.presentation.mapper;

import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDetailsResponse mappingUserToDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "name", ignore = true)
    void updateUserFromDto(@MappingTarget User user, ApplicationRequest request);
}