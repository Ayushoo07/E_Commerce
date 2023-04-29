package com.example.ecommerce.transformer;

import com.example.ecommerce.Enum.ProductStatus;
import com.example.ecommerce.dto.RequestDto.AddProductRequestDto;
import com.example.ecommerce.dto.ResponseDto.ProductResponseDto;
import com.example.ecommerce.model.Product;

public class ProductTransformer {

    public static Product ProductRequestDtoToProduct(AddProductRequestDto addProductRequestDto){

        return Product.builder()
                .name(addProductRequestDto.getProductName())
                .price(addProductRequestDto.getPrice())
                .productCategory(addProductRequestDto.getProductCategory())
                .quantity(addProductRequestDto.getQuantity())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }

    public static ProductResponseDto ProductToProductResponseDto(Product product){
        return ProductResponseDto.builder()
                .productName(product.getName())
                .sellerName(product.getSeller().getName())
                .quantity(product.getQuantity())
                .productStatus(product.getProductStatus())
                .build();
    }
}
