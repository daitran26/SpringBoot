package com.spring.baitap10.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.baitap10.model.Product;



public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("SELECT p FROM Product p WHERE p.category.id=?1")
    List<Product> findAllByCategotyId(Long id);
	
	@Query(value = "SELECT * FROM Product WHERE product.category_id=?1 GROUP BY product.id limit ?2, 4", nativeQuery = true)
	List<Product> findAllByCategotyId(Long id, int index);
	//lấy 4 sp mới nhất
    List<Product> findTop4ByOrderByIdDesc();
    @Query(value = "SELECT * FROM Product GROUP BY product.id limit ?1, 4", nativeQuery = true)
    List<Product> findAllProduct(int index);
    
    Page<Product> findByNameContaining(String name,Pageable pageable);

    
    Page<Product> findByPriceBetween(double min, double max,Pageable pageable);
    
    //page
    Page<Product> findAllByCategory_id(Long id,Pageable pageable);
}
