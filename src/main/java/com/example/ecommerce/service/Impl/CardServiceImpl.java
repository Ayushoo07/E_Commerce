package com.example.ecommerce.service.Impl;

import com.example.ecommerce.dto.RequestDto.CardRequestDto;
import com.example.ecommerce.dto.ResponseDto.CardResponseDto;
import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;import com.example.ecommerce.exception.InvalidCustomerException;
import com.example.ecommerce.model.Card;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.CardService;
import com.example.ecommerce.transformer.CardTransformer;
import com.example.ecommerce.transformer.CustomerTransformer;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
  @Autowired CustomerRepository customerRepository;

  public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerException {

        Customer customer = customerRepository.findByMobNo(cardRequestDto.getMobNo());
        if(customer==null){
            throw new InvalidCustomerException("Sorry! The customer doesn't exists");
        }

    Card card = CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);

        customer.getCards().add(card);
        customerRepository.save(customer);

        // response dto
        return CardResponseDto.builder()
                .customerName(customer.getName())
                .cardNo(card.getCardNo())
                .build();

    }
    public List<CustomerResponseDto> getAllCustomerWithCard(String card) {
      List<Customer> customers=customerRepository.findAll();
      List<CustomerResponseDto> customerResponseDtos = new ArrayList<>();
      for (Customer customer:customers)
      {
          for (Card c:customer.getCards())
          {
              if(c.getCardType().toString().equals(card))
              {
                  CustomerResponseDto obj=CustomerTransformer.CustomerToCustomerResponseDto(customer);
                  customerResponseDtos.add(obj);
              }
          }
      }
      return customerResponseDtos;
    }
}
