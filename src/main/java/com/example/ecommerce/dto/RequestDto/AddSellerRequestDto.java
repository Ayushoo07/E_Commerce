package com.example.ecommerce.dto.RequestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddSellerRequestDto {

    String name;

    String emailId;

    Integer age;

    String mobNo;
}
