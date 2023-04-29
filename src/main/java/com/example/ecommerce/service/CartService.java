package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.CheckoutCartRequestDto;import com.example.ecommerce.dto.ResponseDto.CartResponseDto;
import com.example.ecommerce.dto.ResponseDto.OrderResponseDto;import com.example.ecommerce.model.Cart;import com.example.ecommerce.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    public CartResponseDto saveCart(Integer customerId, Item item);
    public OrderResponseDto checkOutCart(CheckoutCartRequestDto checkoutCartRequestDto) throws Exception;
    public void resetCart(Cart cart);
}
