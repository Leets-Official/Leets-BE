package land.leets.domain.user.presentation.dto;

import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDetailsResponse {

    private UUID uid;
    private String sid;
    private String name;
    private String phone;
    private String email;
    private String major;
    private SubmitStatus submitStatus;

    public static UserDetailsResponse of(User user, SubmitStatus submitStatus) {
        return new UserDetailsResponse(
                user.getUid(),
                user.getSid(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                null,
                submitStatus
        );
    }
}
