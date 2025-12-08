package alzpaCare.server.productLike.entity;

import jakarta.persistence.*;
import lombok.*;

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
