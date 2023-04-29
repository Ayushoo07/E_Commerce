package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RequestDto.CardRequestDto;
import com.example.ecommerce.dto.ResponseDto.CardResponseDto;
import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;import com.example.ecommerce.exception.InvalidCustomerException;
import com.example.ecommerce.service.Impl.CardServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

  @Autowired CardServiceImpl cardServiceImpl;

  @PostMapping("/add")
  public ResponseEntity addCard(@RequestBody CardRequestDto cardRequestDto) {
        try{
            CardResponseDto cardResponseDto = cardServiceImpl.addCard(cardRequestDto);
      return new ResponseEntity(cardResponseDto, HttpStatus.CREATED);
    } catch (InvalidCustomerException e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    // get all VISA cards
    @GetMapping("/getAllCustomerWithCard/{card}")
    public ResponseEntity getAllCustomerWithCard(@PathVariable("card") String card)
    {
        List<CustomerResponseDto> customers=cardServiceImpl.getAllCustomerWithCard(card);
        return new ResponseEntity(customers, HttpStatus.OK);
    }

    // get all MASTERCARD cards whose expirt is greater than 1 Jan 2025

    // Return the CardType which has maximum number of that card

}