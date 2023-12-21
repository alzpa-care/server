package alzpaCare.server.patient;

import alzpaCare.server.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.Year;

@Entity
@Table(name = "patients")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;

    @Column(length = 20)
    private String patientName;

    @Column(length = 20)
    private String diseaseName;

    @Column(length = 10)
    private String birth;

    @Column(length = 10)
    private String grade;

    @Column(length = 4)
    private Year firstDiagnosisYear;

    @Column(length = 4)
    private Year diagnosisYear;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


}
