package com.example.ecommerce.dto.RequestDto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpdateSellerRequest
{
    String email;
    String name;
    Integer age;
    String mobNo;
}
