package alzpaCare.server.postLike;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    public void addLikePost(String email, Integer postId) {
        Post post = getPostById(postId);
        Member member = getMemberByEmail(email);

        if(postLikeRepository.findByMemberIdAndPostId(member.getMemberId(), post.getPostId()).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_EXIST_VOTE);
        }

        PostLike postLike = PostLike.builder()
                .memberId(member.getMemberId())
                .postId(post.getPostId())
                .build();

        postLikeRepository.save(postLike);

    }

    public void deleteLikePost(String email, Integer postId) {
        Post post = getPostById(postId);
        Member member = getMemberByEmail(email);

        PostLike postLike = postLikeRepository.findByMemberIdAndPostId(member.getMemberId(), post.getPostId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_LIKE_NOT_FOUNT));



        postLikeRepository.delete(postLike);
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }


}
