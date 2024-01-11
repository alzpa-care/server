package alzpaCare.server.ProductLike.repository;

import alzpaCare.server.ProductLike.entity.ProductLike;
import alzpaCare.server.ProductLike.entity.ProductLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeId> {

    List<ProductLike> findByMemberId(Integer memberId);
    Optional<ProductLike> findByMemberIdAndProductId(Integer memberId, Integer productId);


}
