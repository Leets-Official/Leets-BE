package land.leets.domain.user;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

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
}
