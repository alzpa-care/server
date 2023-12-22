package alzpaCare.server.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> postProduct(
            @RequestBody @Valid ProductRequest productRequest, Authentication authentication) {

        String username = authentication.getName();

        Product product = productService.createProduct(productRequest.toEntity(), username);

        ProductResponse productResponse = ProductMapper.toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }
}
