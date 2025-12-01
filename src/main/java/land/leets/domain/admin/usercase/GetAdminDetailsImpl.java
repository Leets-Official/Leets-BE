package land.leets.domain.admin.usercase;

import land.leets.domain.admin.domain.Admin;
import land.leets.domain.admin.domain.repository.AdminRepository;
import land.leets.domain.admin.exception.AdminNotFoundException;
import land.leets.domain.admin.presentation.dto.AdminDetailsResponse;
import land.leets.domain.auth.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAdminDetailsImpl implements GetAdminDetails {

    private final AdminRepository adminRepository;

    @Override
    public AdminDetailsResponse execute(AuthDetails authDetails) {
        UUID uid = authDetails.getUid();
        Admin admin = adminRepository.findById(uid).orElseThrow(AdminNotFoundException::new);
        return AdminDetailsResponse.from(admin);
    }
}
