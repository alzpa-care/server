package alzpaCare.server.product;

import alzpaCare.server.audit.Auditable;
import alzpaCare.server.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(length = 10, nullable = false)
    private Category category;

    @Column(nullable = false)
    private int price;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 10000, nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String areas;

    @Column(length = 254)
    private String[] imgUrl;

    @Column(length = 1, columnDefinition = "CHAR DEFAULT 'N'")
    private String soldOutYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_member_id")
    @JsonIgnoreProperties("sellerMember")
    private Member sellerMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_member_id")
    @JsonIgnoreProperties("buyerMember")
    private Member buyerMember;




}
