package alzpaCare.server.patient.event;

import alzpaCare.server.member.entity.Member;
import org.springframework.context.ApplicationEvent;

public class MemberCreatedEvent extends ApplicationEvent {

    private final Member member;

    public MemberCreatedEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }


}
