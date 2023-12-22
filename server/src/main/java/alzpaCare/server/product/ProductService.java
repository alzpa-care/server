package alzpaCare.server.product;

import alzpaCare.server.advice.BusinessLogicException;
import alzpaCare.server.advice.ExceptionCode;
import alzpaCare.server.member.entity.Member;
import alzpaCare.server.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
