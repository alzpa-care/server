package alzpaCare.server.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNameAndBirthAndPhoneNumber(String name, String birth, String phoneNumber);
    Optional<Member> findByEmailAndNameAndPhoneNumberAndBirth(
            String email, String name, String phoneNumber, String birth);


}
