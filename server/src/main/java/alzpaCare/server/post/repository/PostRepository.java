package alzpaCare.server.post.repository;

import alzpaCare.server.post.entity.Post;
import alzpaCare.server.post.entity.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findByPostTypeOrderByCreatedAtDesc(PostType postType, Pageable pageable);
    Page<Post> findByPostTypeOrderByLikeCntDesc(PostType postType, Pageable pageable);
    Page<Post> findByPostTypeOrderByViewCntDesc(PostType postType, Pageable pageable);


}
