package com.example.ecommerce.repository;

import com.example.ecommerce.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;import org.springframework.stereotype.Repository;import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>
{
    Customer findByMobNo(String mobNo);

    @Query(value = "Select * from Customer c where c.age>:age",nativeQuery = true)
    List<Customer> getCustomerAboveAge(Integer age);

    Customer findByEmailId(String emailId);
}
