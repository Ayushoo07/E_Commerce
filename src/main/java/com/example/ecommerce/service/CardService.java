package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.CardRequestDto;import com.example.ecommerce.dto.ResponseDto.CardResponseDto;import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;import com.example.ecommerce.exception.InvalidCustomerException;import org.springframework.stereotype.Service;import java.util.List;

@Service
public interface CardService
{
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerException;
    public List<CustomerResponseDto> getAllCustomerWithCard(String card);
}
