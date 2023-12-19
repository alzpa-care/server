package alzpaCare.server.member.repository;

import alzpaCare.server.member.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNameAndBirthAndPhoneNumber(String name, String birth, String phoneNumber);
    Optional<Member> findByEmailAndNameAndPhoneNumberAndBirth(
            String email, String name, String phoneNumber, String birth);

    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);


}
