package alzpaCare.server.product.service;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import alzpaCare.server.product.entity.Category;
import alzpaCare.server.product.entity.Product;
import alzpaCare.server.product.repository.ProductRepository;
import alzpaCare.server.product.request.BuyerRequest;
import alzpaCare.server.product.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;


    public Product createProduct(Product product, String email) {
        Member sellerMember = getMemberByEmail(email);

        product.setSellerMember(sellerMember);
        return productRepository.save(product);
    }

    public Product updateProduct(Integer productId, ProductRequest productRequest, String email) {
        Product product = getProductById(productId);

        if(!product.getSellerMember().getEmail().equals(email)) {
            new BusinessLogicException(ExceptionCode.SELLER_MEMBER_NOT_MATCH);
        }

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

    public Product updateBuyer(Integer productId, BuyerRequest buyerRequest, String email) {
        Product product = getProductById(productId);
        Member member = getMemberByEmail(email);

        if(!product.getSellerMember().getEmail().equals(email)) {
            new BusinessLogicException(ExceptionCode.SELLER_MEMBER_NOT_MATCH);
        }

        product.setSoldOutYn("Y");
        Member buyerMember = getMemberById(buyerRequest.memberId());

        product.setBuyerMember(buyerMember);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer productId) {
        return getProductById(productId);
    }

//    @Transactional(readOnly = true)
//    public List<Product> getProducts(Integer category, String order, int completed) {
//
//        List<Product> products = productRepository.findProductsByFilters(
//                (category != null) ? Category.values()[category - 1] : null,
//                order,
//                completed
//        );
//
//        return products;
//    }

    @Transactional(readOnly = true)
    public Page<Product> getProducts(int page, int size, Integer category, String order, int completed) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Product> productsPage = productRepository.findProductsByFilters(
                (category != null) ? Category.values()[category - 1] : null,
                order,
                completed,
                pageable
        );

        return productsPage;
    }

    public void productDelete(Integer productId, String email) {
        Product product = getProductById(productId);

        Member member = getMemberByEmail(email);

        if ("Y".equals(product.getSoldOutYn())) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_DELETE_SOLD_OUT);
        } else if (!Objects.equals(product.getSellerMember(), member)) {
            throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_DELETE_MEMBER);
        } else {
            productRepository.delete(product);
        }
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Member getMemberById(Integer memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    private Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
    }


}
