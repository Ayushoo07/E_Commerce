package com.example.ecommerce.service;

import com.example.ecommerce.dto.RequestDto.AddSellerRequestDto;import com.example.ecommerce.dto.RequestDto.GetSellerEmailRequest;import com.example.ecommerce.dto.RequestDto.UpdateSellerRequest;import com.example.ecommerce.dto.ResponseDto.SellerResponseDto;import com.example.ecommerce.exception.EmailAlreadyPresentException;import com.example.ecommerce.exception.InvalidEmail;import org.springframework.http.ResponseEntity;import org.springframework.stereotype.Service;import java.util.List;

@Service
public interface SellerService
{
    public SellerResponseDto addSeller(AddSellerRequestDto addSellerRequestDto) throws EmailAlreadyPresentException;
    public SellerResponseDto getSellerbyEmail(String email)throws InvalidEmail;
    public List<SellerResponseDto> allSeller();
    public ResponseEntity updateSellerByEmail(UpdateSellerRequest updateSellerRequest)throws InvalidEmail;
    public ResponseEntity deleteSellerByEmail(GetSellerEmailRequest getSellerEmailRequest)throws InvalidEmail;
    public List<SellerResponseDto> getSellersOfAge(Integer age);
}
