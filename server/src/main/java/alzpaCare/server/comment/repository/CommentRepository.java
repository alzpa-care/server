package alzpaCare.server.comment.repository;

import alzpaCare.server.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c FROM Comment c WHERE c.product.productId = :productId AND c.parent IS NULL ORDER BY c.createdAt ASC")
    List<Comment> findParentCommentsByProductIdOrderByCreatedAt(@Param("productId") Integer productId);

    @Query("SELECT c FROM Comment c WHERE c.parent.commentId = :parentId ORDER BY c.createdAt ASC")
    List<Comment> findRepliesByParentIdOrderByCreatedAt(@Param("parentId") Integer parentId);


}
