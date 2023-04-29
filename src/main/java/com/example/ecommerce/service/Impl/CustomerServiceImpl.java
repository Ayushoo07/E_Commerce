package com.example.ecommerce.service.Impl;

import com.example.ecommerce.dto.RequestDto.CustomerRequestDto;
import com.example.ecommerce.dto.RequestDto.UpdateCustomerRequest;import com.example.ecommerce.dto.ResponseDto.CustomerResponseDto;
import com.example.ecommerce.exception.InvalidCustomerException;import com.example.ecommerce.exception.MobileNoAlreadyPresentException;
import com.example.ecommerce.model.Card;import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.repository.CardRespository;import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.service.CustomerService;
import com.example.ecommerce.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;import java.util.ArrayList;import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired CustomerRepository customerRepository;
  @Autowired CardRespository cardRespository;

  public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto)
      throws MobileNoAlreadyPresentException {

        if(customerRepository.findByMobNo(customerRequestDto.getMobNo())!=null)
            throw new MobileNoAlreadyPresentException("Sorry! Customer already exists!");

    // request dto -> customer
    Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .cartTotal(0)
                .numberOfItems(0)
                .customer(customer)
                .build();
        customer.setCart(cart);



        Customer savedCustomer = customerRepository.save(customer);  // customer and cart

        // prepare response dto
        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);
    }

    public List<CustomerResponseDto> getAllCustomers()
    {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerResponseDto> customerResponseDtoList=new ArrayList<>();
        for (Customer customer: customers)
        {
            CustomerResponseDto obj=CustomerTransformer.CustomerToCustomerResponseDto(customer);
            customerResponseDtoList.add(obj);
        }
        return customerResponseDtoList;
    }
    public CustomerResponseDto getCustomerByMobNo(String mobNo)
    {
        Customer customer=customerRepository.findByMobNo(mobNo);
        return CustomerTransformer.CustomerToCustomerResponseDto(customer);
    }
    public List<CustomerResponseDto> getCustomerAboveAge(Integer age)
    {
        List<Customer> customers = customerRepository.getCustomerAboveAge(age);
        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();

        for (Customer customer: customers) {
            CustomerResponseDto obj=CustomerTransformer.CustomerToCustomerResponseDto(customer);
            customerResponseDtoList.add(obj);
        }
        return customerResponseDtoList;
    }
    public void deleteCustomerBymobNo(String mobNo)throws InvalidCustomerException
    {

        Customer customer=customerRepository.findByMobNo(mobNo);
        if (customer == null) {
            throw new InvalidCustomerException("Customer Mobno does not exist");
        }
        List<Card> cards=customer.getCards();
        for (Card card: cards) {
            cardRespository.delete(card);
        }
        customerRepository.delete(customer);
    }

    public CustomerResponseDto updateByEmail(UpdateCustomerRequest updateCustomerRequest)throws MobileNoAlreadyPresentException
    {
    Customer customer = customerRepository.findByEmailId(updateCustomerRequest.getEmail());

    if(!updateCustomerRequest.getAddress().equals(""))
        customer.setAddress(updateCustomerRequest.getAddress());
    if (updateCustomerRequest.getAge() != 0)
        customer.setAge(updateCustomerRequest.getAge());
    if (!updateCustomerRequest.getMobNo().equals(""))
    {
        if(customerRepository.findByMobNo(updateCustomerRequest.getMobNo())!=null)
            throw new MobileNoAlreadyPresentException("Mobile number already present in the database");
        else
            customer.setMobNo(updateCustomerRequest.getMobNo());
    }

    customerRepository.save(customer);

    return CustomerTransformer.CustomerToCustomerResponseDto(customer);

    }
}
