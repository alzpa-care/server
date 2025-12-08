package alzpaCare.server.productLike.repository;

import alzpaCare.server.productLike.entity.ProductLike;
import alzpaCare.server.productLike.entity.ProductLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeId> {

    List<ProductLike> findByMemberId(Integer memberId);

    Optional<ProductLike> findByMemberIdAndProductId(Integer memberId, Integer productId);


}
