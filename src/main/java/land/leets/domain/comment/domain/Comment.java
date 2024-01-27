package land.leets.domain.comment.domain;

import jakarta.persistence.*;
import land.leets.domain.admin.domain.Admin;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.*;

@Entity(name = "comments")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long applicationId;

    @Column(nullable = false)
    private String content;

    @OneToOne
    private Admin admin;
}
