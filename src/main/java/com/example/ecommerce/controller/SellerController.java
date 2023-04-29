package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RequestDto.AddSellerRequestDto;
import com.example.ecommerce.dto.RequestDto.GetSellerEmailRequest;
import com.example.ecommerce.dto.RequestDto.UpdateSellerRequest;
import com.example.ecommerce.dto.ResponseDto.SellerResponseDto;
import com.example.ecommerce.exception.InvalidEmail;
import com.example.ecommerce.service.Impl.SellerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerServiceImpl sellerServiceImpl;

    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody AddSellerRequestDto addSellerRequestDto){
        try{
            SellerResponseDto sellerResponseDto = sellerServiceImpl.addSeller(addSellerRequestDto);
            return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getSellerbyEmail/{email}")
    public SellerResponseDto getSellerbyEmail(@PathVariable("email") String email)throws InvalidEmail
    {
        return sellerServiceImpl.getSellerbyEmail(email);
    }

    // get by id

    // get all seller
    @GetMapping("/getAllSeller")
    public ResponseEntity getAllSeller()
    {
        return new ResponseEntity(sellerServiceImpl.allSeller(),HttpStatus.OK);
    }

    // update seller info based on email id
    @PutMapping("updateSellerByEmail")
    public ResponseEntity updateSellerByEmail(@RequestBody UpdateSellerRequest updateSellerEmailRequest)throws InvalidEmail
    {
        return sellerServiceImpl.updateSellerByEmail(updateSellerEmailRequest);
    }

    // delete a seller based on email
    @DeleteMapping("/deleteSellerByEmail")
    public ResponseEntity deleteSellerByEmail(@RequestBody GetSellerEmailRequest getSellerEmailRequest)throws InvalidEmail
    {
        return sellerServiceImpl.deleteSellerByEmail(getSellerEmailRequest);
    }
    //delete by id

    // get all sellers of a particular age
    @GetMapping("/getSellersOfAge/{age}")
    public ResponseEntity getSellersofAge(@PathVariable("age") Integer age)
    {
        List<SellerResponseDto> sellers=sellerServiceImpl.getSellersOfAge(age);

        return new ResponseEntity(sellers,HttpStatus.OK);
    }
}
