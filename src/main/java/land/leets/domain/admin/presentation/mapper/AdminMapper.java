package land.leets.domain.admin.presentation.mapper;

import land.leets.domain.admin.domain.Admin;
import land.leets.domain.admin.presentation.dto.AdminDetailsResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AdminDetailsResponse mappingAdminToDto(Admin admin);
}
