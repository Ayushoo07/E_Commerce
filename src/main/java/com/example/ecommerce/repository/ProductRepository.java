package com.example.ecommerce.repository;

import com.example.ecommerce.Enum.ProductCategory;
import com.example.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findByProductCategory(ProductCategory productCategory);

}
