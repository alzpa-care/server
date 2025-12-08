package alzpaCare.server.productComment.service;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.productComment.entity.Comment;
import alzpaCare.server.productComment.repository.CommentRepository;
import alzpaCare.server.productComment.response.CommentResponse;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.mapper.MemberMapper;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.product.entity.Product;
import alzpaCare.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Comment createComment(Integer productId, Comment comment, String email) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(productId);

        comment.setMember(member);
        comment.setProduct(product);
        comment.setCommentType("댓글");

        Comment savedComment = commentRepository.save(comment);
        // 댓글이 성공적으로 저장되면 댓글 카운트를 증가시킨다
        commentCountAdd(product);

        return savedComment;
    }

    public Comment createReply(Integer parentId, Comment reply, String email) {
        Member member = getMemberByEmail(email);
        Comment parentComment = getCommentById(parentId);

        reply.setParent(parentComment);
        reply.setProduct(parentComment.getProduct());
        reply.setMember(member);
        reply.setCommentType("대댓글");

        Comment savedReply = commentRepository.save(reply);

        commentCountAdd(parentComment.getProduct());

        // 부모 댓글에 대댓글을 추가한다
        parentComment.getReply().add(savedReply);

        return savedReply;
    }

    public List<CommentResponse> getCommentResponsesByProduct(Integer productId, String email) {
        List<CommentResponse> result = new ArrayList<>();
        List<Comment> parentComments = commentRepository.findParentCommentsByProductIdOrderByCreatedAt(productId);

        Member member = getMemberByEmail(email);
        Product product = getProductById(productId);

        boolean isPostAuthor = product.getSellerMember().getEmail().equals(email);

        for (Comment parentComment : parentComments) {
            boolean isCommentAuthor = parentComment.getMember().getEmail().equals(email);

            String modifiedContent;
            if (isPostAuthor || isCommentAuthor) {
                modifiedContent = parentComment.getContent();
            } else {
                modifiedContent = "비밀댓글 입니다.";
            }

            CommentResponse parentResponse = new CommentResponse(
                    parentComment.getCommentId(),
                    modifiedContent,
                    parentComment.getCreatedAt(),
                    parentComment.getCommentType(),
                    MemberMapper.toMemberSummaryResponse(parentComment.getMember())
            );
            result.add(parentResponse);

            List<Comment> replies = commentRepository.findRepliesByParentIdOrderByCreatedAt(parentComment.getCommentId());

            for (Comment reply : replies) {
                boolean isReplyAuthor = reply.getMember().getEmail().equals(email) || isPostAuthor || isCommentAuthor;

                String modifiedReplyContent;
                if (isReplyAuthor) {
                    modifiedReplyContent = reply.getContent();
                } else {
                    modifiedReplyContent = "비밀댓글 입니다.";
                }

                CommentResponse replyResponse = new CommentResponse(
                        reply.getCommentId(),
                        modifiedReplyContent,
                        reply.getCreatedAt(),
                        reply.getCommentType(),
                        MemberMapper.toMemberSummaryResponse(reply.getMember())
                );
                result.add(replyResponse);
            }
        }

        return result;
    }

    public Comment updateComment(Integer commentId, Comment updatedComment, String email) {
        Comment comment = getCommentById(commentId);

        if (!comment.getMember().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        comment.setContent(updatedComment.getContent());

        return commentRepository.save(comment);
    }

    public void deleteComment(Integer commentId, String email) {
        Comment comment = getCommentById(commentId);

        if (!comment.getMember().getEmail().equals(email)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }

        if (!comment.getReply().isEmpty()) {
            comment.setContent("삭제된 댓글입니다.");
            comment.setDeleteYn("Y");
            commentRepository.save(comment);
        } else {
            commentRepository.delete(comment);
        }

        commentCountSubtract(comment.getProduct());
    }

    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
    }

    private void commentCountAdd(Product product) {
        product.setCommentCount(product.getCommentCount() + 1);
        productRepository.save(product);
    }

    private void commentCountSubtract(Product product) {
        int currentCount = product.getCommentCount();
        if (currentCount > 0) {
            product.setCommentCount(currentCount - 1);
            productRepository.save(product);
        }
    }


}
