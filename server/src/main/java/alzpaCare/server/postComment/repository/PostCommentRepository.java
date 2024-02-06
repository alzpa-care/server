package alzpaCare.server.postComment.repository;

import alzpaCare.server.postComment.entity.PostComment;
import alzpaCare.server.productComment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {
    @Query("SELECT c FROM PostComment c WHERE c.post.postId = :postId AND c.parent IS NULL ORDER BY c.createdAt ASC")
    List<PostComment> findParentCommentsByPostIdOrderByCreatedAt(@Param("postId") Integer postId);

    @Query("SELECT c FROM PostComment c WHERE c.parent.commentId = :parentId ORDER BY c.createdAt ASC")
    List<PostComment> findRepliesByParentIdOrderByCreatedAt(@Param("parentId") Integer parentId);


}
