package alzpaCare.server.postLike;

import alzpaCare.server.member.entity.Member;
import alzpaCare.server.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    Optional<PostLike> findByMemberIdAndPostId(Integer memberId, Integer postId);

}
