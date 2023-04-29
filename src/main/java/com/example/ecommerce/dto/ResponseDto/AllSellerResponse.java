package com.example.ecommerce.dto.ResponseDto;

import com.example.ecommerce.model.Seller;import lombok.*;
import lombok.experimental.FieldDefaults;import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllSellerResponse
{
    List<Seller> sellers;
}
