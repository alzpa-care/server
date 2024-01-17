package alzpaCare.server.ProductLike.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@IdClass(ProductLikeId.class)
public class ProductLike {

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "product_id")
    private Integer productId;


}
