package alzpaCare.server.member.entity;


import alzpaCare.server.audit.Auditable;
import alzpaCare.server.patient.entity.Patient;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

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

    @Column(unique = true, updatable = false, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, updatable = false, nullable = false)
    private String name;

    @Column(length = 12, nullable = false)
    private String nickname;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 10, updatable = false, nullable = false)
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

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_role")})
    private Set<Authority> authorities;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Patient patient;


}