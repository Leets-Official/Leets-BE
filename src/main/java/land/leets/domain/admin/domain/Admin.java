package land.leets.domain.admin.domain;

import jakarta.persistence.*;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "admins")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    private String refreshToken;
}
