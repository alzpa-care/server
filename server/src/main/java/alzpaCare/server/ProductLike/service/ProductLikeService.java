package alzpaCare.server.ProductLike.service;

import alzpaCare.server.ProductLike.entity.ProductLike;
import alzpaCare.server.ProductLike.repository.ProductLikeRepository;
import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.product.entity.Product;
import alzpaCare.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public void createLikeProduct(Integer productId, String email) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(productId);

        ProductLike productLike = new ProductLike();
        productLike.setMemberId(member.getMemberId());
        productLike.setProductId(product.getProductId());

        productLikeRepository.save(productLike);
    }

    @Transactional(readOnly = true)
    public List<Product> getLikedProductsByMember(String email) {
        Member member = getMemberByEmail(email);

        List<ProductLike> likedProducts = productLikeRepository.findByMemberId(member.getMemberId());

        return likedProducts.stream()
                .map(productLike -> productRepository.findById(productLike.getProductId()).orElse(null))
                .filter(product -> product != null)
                .toList();
    }

    public void deleteProductLike(Integer productId, String email) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(productId);

        productLikeRepository.findByMemberIdAndProductId(member.getMemberId(), product.getProductId())
                .ifPresent(productLikeRepository::delete);
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
    }


}
