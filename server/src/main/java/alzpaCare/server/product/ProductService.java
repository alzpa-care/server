package alzpaCare.server.product;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;


    public Product createProduct(Product product, String email) {
        Member sellerMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        product.setSellerMember(sellerMember);
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, ProductRequest productRequest, String email) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        Optional.ofNullable(productRequest.category())
                .ifPresent(product::setCategory);
        Optional.ofNullable(productRequest.price())
                .ifPresent(product::setPrice);
        Optional.ofNullable(productRequest.title())
                .ifPresent(product::setTitle);
        Optional.ofNullable(productRequest.content())
                .ifPresent(product::setContent);
        Optional.ofNullable(productRequest.areas())
                .ifPresent(product::setAreas);
        Optional.ofNullable(productRequest.imgUrl())
                .ifPresent(product::setImgUrl);

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
    }

    public Product updateBuyer(Integer productId, BuyerRequest buyerRequest, String email) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        product.setSoldOutYn("Y");
        Member buyerMember = memberRepository.findById(buyerRequest.memberId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        product.setBuyerMember(buyerMember);
        return productRepository.save(product);
    }

    public void productDelete(Integer productId, String email) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if ("Y".equals(product.getSoldOutYn())) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_DELETE_SOLD_OUT);
        } else if (!Objects.equals(product.getSellerMember(), member)) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_DELETE_MEMBER);
        } else {
            productRepository.delete(product);
        }
    }

    public List<Product> getProducts(Integer category, String order, int completed) {

        List<Product> products = productRepository.findProductsByFilters(
                (category != null) ? Category.values()[category - 1] : null,
                order,
                completed
        );

        return products;
    }


}
