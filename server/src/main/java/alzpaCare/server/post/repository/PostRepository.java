package alzpaCare.server.post.repository;

import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByPostTypeOrderByCreatedAtDesc(PostType postType);
    List<Post> findByPostTypeOrderByLikeCountDesc(PostType postType);
    List<Post> findByPostTypeOrderByViewCountDesc(PostType postType);


}
