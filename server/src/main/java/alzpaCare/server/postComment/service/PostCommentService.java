package alzpaCare.server.postComment.service;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.repository.PostRepository;
import alzpaCare.server.postComment.entity.PostComment;
import alzpaCare.server.postComment.repository.PostCommentRepository;
import alzpaCare.server.postComment.response.PostCommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public PostComment createComment(Integer postId, PostComment postComment, String email) {
        Member member = getMemberByEmail(email);
        Post post = getPostById(postId);

        postComment.setMember(member);
        postComment.setPost(post);

        PostComment savedPostComment = postCommentRepository.save(postComment);
        // 댓글이 성공적으로 저장되면 댓글 카운트를 증가시킨다
        commentCountAdd(post);

        return savedPostComment;
    }

    public PostComment createReply(Integer parentId, PostComment reply, String email) {
        Member member = getMemberByEmail(email);
        PostComment parentPostComment = getCommentById(parentId);

        reply.setParent(parentPostComment);
        reply.setPost(parentPostComment.getPost());
        reply.setMember(member);

        PostComment savedReply = postCommentRepository.save(reply);

        commentCountAdd(parentPostComment.getPost());

        // 부모 댓글에 대댓글을 추가한다
        parentPostComment.getReply().add(savedReply);

        return savedReply;
    }

    public List<PostCommentResponse> getCommentResponsesByPost(Integer postId) {
        List<PostCommentResponse> result = new ArrayList<>();
        List<PostComment> parentPostComments = postCommentRepository.findParentCommentsByPostIdOrderByCreatedAt(postId);

        for (PostComment parentPostComment : parentPostComments) {
            String modifiedContent = parentPostComment.getContent();

            PostCommentResponse parentResponse = new PostCommentResponse(
                    parentPostComment.getCommentId(),
                    modifiedContent,
                    parentPostComment.getCreatedAt(),
                    MemberMapper.toMemberSummaryResponse(parentPostComment.getMember())
            );
            result.add(parentResponse);

            List<PostComment> replies = postCommentRepository.findRepliesByParentIdOrderByCreatedAt(parentPostComment.getCommentId());

            for (PostComment reply : replies) {
                String modifiedReplyContent = reply.getContent();

                PostCommentResponse replyResponse = new PostCommentResponse(
                        reply.getCommentId(),
                        modifiedReplyContent,
                        reply.getCreatedAt(),
                        MemberMapper.toMemberSummaryResponse(reply.getMember())
                );
                result.add(replyResponse);
            }
        }

        return result;
    }

    public PostComment updateComment(Integer commentId, PostComment updatedPostComment, String email) {
        PostComment postComment = getCommentById(commentId);

        postComment.setContent(updatedPostComment.getContent());

        return postCommentRepository.save(postComment);
    }

    public void deleteComment(Integer commentId, String email) {
        PostComment postComment = getCommentById(commentId);

        if (!postComment.getMember().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        if (!postComment.getReply().isEmpty()) {
            postComment.setContent("삭제된 댓글입니다.");
            postComment.setDeleteYn("Y");
            postCommentRepository.save(postComment);
        } else {
            postCommentRepository.delete(postComment);
        }

        commentCountSubtract(postComment.getPost());
    }

    public PostComment getCommentById(Integer commentId) {
        return postCommentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
    }

    private void commentCountAdd(Post post) {
        post.setCommentCnt(post.getCommentCnt() + 1);
        postRepository.save(post);
    }

    private void commentCountSubtract(Post post) {
        int currentCount = post.getCommentCnt();
        if (currentCount > 0) {
            post.setCommentCnt(currentCount - 1);
            postRepository.save(post);
        }
    }


}
