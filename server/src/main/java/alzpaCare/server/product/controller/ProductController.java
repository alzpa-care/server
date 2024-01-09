package alzpaCare.server.product.controller;

import alzpaCare.server.product.response.ProductResponse;
import alzpaCare.server.product.service.ProductService;
import alzpaCare.server.product.entity.Product;
import alzpaCare.server.product.request.BuyerRequest;
import alzpaCare.server.product.request.ProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static alzpaCare.server.product.response.ProductResponse.toProductResponse;

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
        ProductResponse productResponse = toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> patchProduct(
            @PathVariable("productId") Integer productId,
            @RequestBody @Valid ProductRequest productRequest,
            Authentication authentication) {

        String username = authentication.getName();

        Product product = productService.updateProduct(productId, productRequest, username);
        ProductResponse productResponse = toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("/buyer/{productId}")
    public ResponseEntity<ProductResponse> patchBuyer(
            @PathVariable("productId") Integer productId,
            @RequestBody @Valid BuyerRequest buyerRequest,
            Authentication authentication) {

        String username = authentication.getName();

        Product product = productService.updateBuyer(productId, buyerRequest, username);
        ProductResponse productResponse = toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @PathVariable("productId") Integer productId) {

        Product product = productService.findProductById(productId);
        ProductResponse productResponse = toProductResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity getProducts(
            @RequestParam int category,
            @RequestParam(defaultValue = "newest") String order,
            @RequestParam(defaultValue = "0") int completed
    ) {
        List<Product> products = productService.getProducts(category, order, completed);

        List<ProductResponse> productResponses = products.stream()
                .map(ProductResponse::toProductResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponses);

    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("productId") Integer productId, Authentication authentication) {

        String username = authentication.getName();

        productService.productDelete(productId, username);

        return ResponseEntity.ok("글이 성공적으로 삭제되었습니다.");
    }


}
