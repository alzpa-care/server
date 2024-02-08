package alzpaCare.server.product.repository;


import alzpaCare.server.product.entity.Category;
import alzpaCare.server.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

//    @Query("SELECT p FROM Product p WHERE (:category is null or p.category = :category) " +
//            "AND (:completed = 1 or p.soldOutYn = 'N') " +
//            "ORDER BY " +
//            "CASE WHEN :order = 'low' THEN p.price END ASC, " +
//            "CASE WHEN :order = 'high' THEN p.price END DESC, " +
//            "p.createdAt DESC")
//    List<Product> findProductsByFilters(
//            @Param("category") Category category,
//            @Param("order") String order,
//            @Param("completed") int completed
//    );

    @Query("SELECT p FROM Product p WHERE (:category is null or p.category = :category) " +
            "AND (:completed = 1 or p.soldOutYn = 'N') " +
            "ORDER BY " +
            "CASE WHEN :order = 'low' THEN p.price END ASC, " +
            "CASE WHEN :order = 'high' THEN p.price END DESC, " +
            "p.createdAt DESC")
    Page<Product> findProductsByFilters(
            @Param("category") Category category,
            @Param("order") String order,
            @Param("completed") int completed,
            Pageable pageable
    );


}
