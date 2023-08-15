package land.leets.domain.admin.usercase;

import land.leets.domain.admin.presentation.dto.AdminDetailsResponse;
import land.leets.domain.auth.AuthDetails;

public interface GetAdminDetails {
    AdminDetailsResponse execute(AuthDetails authDetails);
}
