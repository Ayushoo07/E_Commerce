package com.example.ecommerce.transformer;

import com.example.ecommerce.dto.RequestDto.AddSellerRequestDto;
import com.example.ecommerce.dto.ResponseDto.SellerResponseDto;
import com.example.ecommerce.model.Seller;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SellerTransformer {

    public static Seller SellerRequestDtoToSeller(AddSellerRequestDto addSellerRequestDto){

        return Seller.builder()
                .name(addSellerRequestDto.getName())
                .age(addSellerRequestDto.getAge())
                .emailId(addSellerRequestDto.getEmailId())
                .mobNo(addSellerRequestDto.getMobNo())
                .build();
    }

    public static SellerResponseDto SellerToSellerResponseDto(Seller seller){

        return SellerResponseDto.builder()
                .name(seller.getName())
                .age(seller.getAge())
                .build();
    }
}
