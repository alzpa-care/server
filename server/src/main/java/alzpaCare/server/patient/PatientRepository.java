package alzpaCare.server.patient;

import alzpaCare.server.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByMemberEmail(String email);

    boolean existsByMember(Member member);


}
