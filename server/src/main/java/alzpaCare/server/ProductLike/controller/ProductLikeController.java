package alzpaCare.server.ProductLike.controller;

import alzpaCare.server.ProductLike.service.ProductLikeService;
import alzpaCare.server.product.entity.Product;
import alzpaCare.server.product.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/productLike")
public class ProductLikeController {

    private final ProductLikeService productLikeService;

    @PostMapping("/{productId}")
    public ResponseEntity<String> postLikeProduct(
            @PathVariable("productId") Integer productId, Authentication authentication) {

        String username = authentication.getName();
        productLikeService.createLikeProduct(productId, username);

        return ResponseEntity.ok("찜이 등록되었습니다.");
    }

    @GetMapping
    public ResponseEntity getLikedProduct(Authentication authentication) {

        String username = authentication.getName();

        List<Product> products = productLikeService.getLikedProductsByMember(username);

        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::toProductResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponses);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteLikeProduct(
            @PathVariable("productId") Integer productId, Authentication authentication) {

        String username = authentication.getName();
        productLikeService.deleteProductLike(productId, username);

        return ResponseEntity.ok("찜이 삭제되었습니다.");
    }


}
