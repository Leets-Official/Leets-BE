package land.leets.domain.user.usecase;

import land.leets.domain.application.presentation.dto.ApplicationRequest;
import land.leets.domain.user.domain.User;

import java.util.UUID;

public interface UpdateUser {
    User execute(UUID uid, ApplicationRequest request);
}
