package land.leets.domain.user.domain;

import jakarta.persistence.*;
import land.leets.domain.shared.BaseTimeEntity;
import land.leets.global.auth.type.AuthProvider;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uid;

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
    private AuthProvider authProvider;

    private String refreshToken;
}
