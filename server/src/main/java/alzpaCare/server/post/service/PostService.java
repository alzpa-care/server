package alzpaCare.server.post.service;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.entity.PostType;
import alzpaCare.server.post.repository.PostRepository;
import alzpaCare.server.post.request.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Post createPost(Post post, String email) {
        Member member = getMemberByEmail(email);

        post.setMember(member);
        return postRepository.save(post);
    }

    public Post updatePost(Integer postId, PostRequest postRequest, String email) {
        Post post = getPostById(postId);

        if(!post.getMember().getEmail().equals(email)) {
            new BusinessLogicException(ExceptionCode.POST_MEMBER_NOT_MATCH);
        }

        Optional.ofNullable(postRequest.postType())
                .ifPresent(post::setPostType);
        Optional.ofNullable(postRequest.title())
                .ifPresent(post::setTitle);
        Optional.ofNullable(postRequest.content())
                .ifPresent(post::setContent);
        Optional.ofNullable(postRequest.imgUrl())
                .ifPresent(post::setImgUrl);

        return postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Post findPostById(Integer postId) { return getPostById(postId); }

    @Transactional(readOnly = true)
    public Page<Post> getPosts(int page, int size, Integer postType, String order) {
        PostType type = PostType.values()[postType - 1];
        Pageable pageable = PageRequest.of(page, size);

        switch (order) {
            case "like":
                return postRepository.findByPostTypeOrderByLikeCntDesc(type, pageable);
            case "view":
                return postRepository.findByPostTypeOrderByViewCntDesc(type, pageable);
            default:
                return postRepository.findByPostTypeOrderByCreatedAtDesc(type, pageable);
        }
    }

    public void postDelete(Integer postId, String email) {
        Post post = getPostById(postId);

        if(!post.getMember().getEmail().equals(email)) {
            new BusinessLogicException(ExceptionCode.POST_MEMBER_NOT_MATCH);
        }

        postRepository.delete(post);
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
