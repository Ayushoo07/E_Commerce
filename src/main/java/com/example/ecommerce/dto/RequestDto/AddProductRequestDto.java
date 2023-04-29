package com.example.ecommerce.dto.RequestDto;

import com.example.ecommerce.Enum.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Builder
public class AddProductRequestDto {

    int sellerId;

    String productName;

    int price;

    int quantity;

    ProductCategory productCategory;
}
