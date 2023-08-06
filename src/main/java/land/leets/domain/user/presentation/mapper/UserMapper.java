package land.leets.domain.user.presentation.mapper;

import land.leets.domain.user.domain.User;
import land.leets.domain.user.presentation.dto.UserDetailsResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDetailsResponse mappingUserToDto(User user);

}