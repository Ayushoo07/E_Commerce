package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.ItemRequestDto;
import com.example.ecommerce.model.Item;
import org.springframework.stereotype.Service;

@Service
public interface ItemService {
    public Item addItem(ItemRequestDto itemRequestDto) throws Exception;
}
