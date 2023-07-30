package land.leets.domain.user;

import jakarta.persistence.*;
import land.leets.domain.shared.AuthRole;
import land.leets.global.auth.user.OAuth2UserInfo;
import land.leets.global.auth.type.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sid;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String major;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthRole role;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String refreshToken;

    private String oauth2Id;

    public User update(OAuth2UserInfo oAuth2UserInfo) {
        this.name = oAuth2UserInfo.getName();
        this.oauth2Id = oAuth2UserInfo.getId();
        return this;
    }


}
