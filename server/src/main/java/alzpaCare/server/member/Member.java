package alzpaCare.server.member;


import alzpaCare.server.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "members")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 12, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 10, nullable = false)
    private String birth;

    @Column(length = 254)
    private String imgUrl;

    @Column(length = 1, nullable = false)
    private String termsOfServiceYn;

    @Column(length = 1, nullable = false)
    private String privacyPolicyYn;

    @Column(length = 1, nullable = false)
    private String locationYn;

    @Column(length = 1, nullable = false)
    private String emailYn;

    @Column(length = 1, columnDefinition = "CHAR DEFAULT 'N'")
    private String deleteYn;


}