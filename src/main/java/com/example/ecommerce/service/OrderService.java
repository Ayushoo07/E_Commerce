package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.OrderRequestDto;import com.example.ecommerce.dto.ResponseDto.OrderResponseDto;import com.example.ecommerce.exception.InvalidCustomerException;import com.example.ecommerce.model.Card;import com.example.ecommerce.model.Customer;import com.example.ecommerce.model.Ordered;import org.springframework.stereotype.Service;import java.util.List;

@Service
public interface OrderService
{
    public Ordered placeOrder(Customer customer, Card card) throws Exception;
    public String generateMaskedCard(String cardNo);
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception;
    public List<OrderResponseDto> getAllOrdersByEmail(String email)throws InvalidCustomerException;
    public List<OrderResponseDto> getrecentorders();
    public OrderResponseDto getOrderWithHighestCost();
}
