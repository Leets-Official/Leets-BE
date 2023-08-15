package land.leets.domain.user.presentation.dto;

import land.leets.domain.application.type.SubmitStatus;
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
}
