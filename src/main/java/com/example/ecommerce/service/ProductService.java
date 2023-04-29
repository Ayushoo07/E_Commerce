package com.example.ecommerce.service;


import com.example.ecommerce.Enum.ProductCategory;import com.example.ecommerce.dto.RequestDto.AddProductRequestDto;
import com.example.ecommerce.dto.RequestDto.GetSellerEmailRequest;import com.example.ecommerce.dto.RequestDto.GetSellerProductIdRequest;import com.example.ecommerce.dto.ResponseDto.ProductResponseDto;
import com.example.ecommerce.exception.InvalidSellerException;
import com.example.ecommerce.model.Item;import org.springframework.stereotype.Service;import java.util.List;

@Service
public interface ProductService {
    public ProductResponseDto addProduct(AddProductRequestDto addProductRequestDto)
            throws InvalidSellerException;

    public List<ProductResponseDto> getAllProductsByCategory(ProductCategory category);
    public List<ProductResponseDto> getProductsBySellerEmail(GetSellerEmailRequest getSellerEmailRequest) throws InvalidSellerException;
    public void deleteProductBySellerProductId(GetSellerProductIdRequest getSellerProductIdRequest);
    public void decreaseProductQuantity(Item item) throws Exception;
}
