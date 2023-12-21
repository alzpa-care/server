package alzpaCare.server.patient;

import alzpaCare.server.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class eventListener {

    private final PatientRepository patientRepository;

    @EventListener
    public void handleMemberCreatedEvent(MemberCreatedEvent event) {

        Member member = event.getMember();

        if (!patientRepository.existsByMember(member)) {
            Patient newPatient = new Patient();
            newPatient.setMember(member);
            patientRepository.save(newPatient);
        }
    }


}
