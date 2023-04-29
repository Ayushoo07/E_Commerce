package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.CustomerRequestDto;import com.example.ecommerce.dto.RequestDto.UpdateCustomerRequest;import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;import com.example.ecommerce.exception.InvalidCustomerException;import com.example.ecommerce.exception.MobileNoAlreadyPresentException;import org.springframework.stereotype.Service;import java.util.List;

@Service
public interface CustomerService
{
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto)
            throws MobileNoAlreadyPresentException;
    public List<CustomerResponseDto> getAllCustomers();
    public CustomerResponseDto getCustomerByMobNo(String mobNo);
    public List<CustomerResponseDto> getCustomerAboveAge(Integer age);
    public void deleteCustomerBymobNo(String mobNo)throws InvalidCustomerException;
    public CustomerResponseDto updateByEmail(UpdateCustomerRequest updateCustomerRequest)throws MobileNoAlreadyPresentException;
}
