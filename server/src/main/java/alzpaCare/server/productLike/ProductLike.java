package alzpaCare.server.productLike;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@IdClass(ProductLikeId.class)
public class ProductLike {

    @Id
    @Column(name = "member_id")
    private int memberId;

    @Id
    @Column(name = "product_id")
    private int productId;


}
