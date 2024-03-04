package alzpaCare.server.productLike.service;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.product.entity.Product;
import alzpaCare.server.product.repository.ProductRepository;
import alzpaCare.server.productLike.entity.ProductLike;
import alzpaCare.server.productLike.repository.ProductLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductLikeService {

    private final ProductLikeRepository productLikeRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public void addLikeProduct(String email, Integer productId) {
        Member member = getMemberByEmail(email);
        Product product = getProductById(productId);

        ProductLike productLike = new ProductLike();
        productLike.setMemberId(member.getMemberId());
        productLike.setProductId(product.getProductId());

        productLikeRepository.save(productLike);
    }


    @Transactional(readOnly = true)
    public Page<Product> getLikedProductsByMember(int page, int size, String email) {
        Member member = getMemberByEmail(email);
        Pageable pageable = PageRequest.of(page, size);

        List<ProductLike> likedProducts = productLikeRepository.findByMemberId(member.getMemberId());
        List<Integer> productIds = likedProducts.stream()
                .map(ProductLike::getProductId)
                .collect(Collectors.toList());

        return productRepository.findByIdIn(productIds, pageable);
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
