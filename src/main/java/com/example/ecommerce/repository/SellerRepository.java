package com.example.ecommerce.repository;

import com.example.ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {

    public Seller findByEmailId(String emailId);

    @Query(value = "Select * from Seller s where s.age=:age",nativeQuery = true)
    List<Seller> getSellersOfAge(Integer age);
}
