package alzpaCare.server.postLike;

import alzpaCare.server.productLike.entity.ProductLikeId;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "postLikes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PostLikeId.class)
public class PostLike {

    @Id
    @Column(name = "member_id")
    private Integer memberId;

    @Id
    @Column(name = "product_id")
    private Integer postId;


}
