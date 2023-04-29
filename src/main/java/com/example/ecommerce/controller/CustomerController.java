package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RequestDto.CustomerRequestDto;
import com.example.ecommerce.dto.RequestDto.UpdateCustomerRequest;import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.ecommerce.exception.InvalidCustomerException;import com.example.ecommerce.exception.MobileNoAlreadyPresentException;
import com.example.ecommerce.service.Impl.CustomerServiceImpl;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
  @Autowired CustomerServiceImpl customerServiceImpl;

  @PostMapping("/add")
  public CustomerResponseDto addCustomer(@RequestBody CustomerRequestDto customerRequestDto)
      throws MobileNoAlreadyPresentException {

    return customerServiceImpl.addCustomer(customerRequestDto);
  }

  // view all customers
  @GetMapping("/getAllCutomers")
  public ResponseEntity getAllCustomers()
  {
    List<CustomerResponseDto> customers=customerServiceImpl.getAllCustomers();
    return new ResponseEntity(customers, HttpStatus.OK);
  }
  // get a customer by email/mob
  @GetMapping("getCustomerByMobNo/{mobNo}")
  public ResponseEntity getCustomerByMobNo(@PathVariable("mobNo") String mobNo)
  {
    return new ResponseEntity(customerServiceImpl.getCustomerByMobNo(mobNo),HttpStatus.FOUND);
  }
  // get all customers whose age is greater than 25
  @GetMapping("/getCustomerAboveAge/{age}")
  public ResponseEntity getCustomerAboveAge(@PathVariable("age") Integer age)
  {
    return new ResponseEntity(customerServiceImpl.getCustomerAboveAge(age), HttpStatus.OK);
  }

  // delete a customer by email/mob
  @DeleteMapping("/deleteCustomerByMobNo/{MobNo}")
  public ResponseEntity deleteCustomerBymobNo(@PathVariable("MobNo") String mobNo)throws InvalidCustomerException
  {
    customerServiceImpl.deleteCustomerBymobNo(mobNo);
    return new ResponseEntity("CustomerDeletedSuccessfully",HttpStatus.OK);
  }

  @PutMapping("updateByEmail")
  public ResponseEntity updateByEmail(@RequestBody UpdateCustomerRequest updateCustomerRequest)throws MobileNoAlreadyPresentException
  {
    return new ResponseEntity(customerServiceImpl.updateByEmail(updateCustomerRequest),HttpStatus.OK);
  }


  // get all customers who use VISA card

  // update a customer info by email


}
