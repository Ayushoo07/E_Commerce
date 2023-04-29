package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RequestDto.OrderRequestDto;
import com.example.ecommerce.dto.ResponseDto.OrderResponseDto;
import com.example.ecommerce.exception.InvalidCustomerException;import com.example.ecommerce.repository.OrderedRepository;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController
{
  // API to order and item individually
  @Autowired OrderService orderService;
    @Autowired
    private OrderedRepository orderedRepository;

  @PostMapping("/place")
  public OrderResponseDto placeDirectOrder(@RequestBody OrderRequestDto orderRequestDto)
      throws Exception {

        return orderService.placeOrder(orderRequestDto);
    }

  // get all the orders for a customer
  @GetMapping("/getAllOrdersByEmail")
  public List<OrderResponseDto> getAllOrdersByEmail(@RequestParam("email") String email)throws InvalidCustomerException
  {
    return orderService.getAllOrdersByEmail(email);
  }

  // get recent 5 orders
  @GetMapping("/getrecentorders")
  public List<OrderResponseDto> getrecentorders(@RequestParam("email") String email)
  {
    return orderService.getrecentorders();
  }
  // select the order and also tell the customer name with the highest total value.
  @GetMapping("/getOrderWithHighestCost")
  public OrderResponseDto getOrderWithHighestCost()
  {
    return orderService.getOrderWithHighestCost();
  }

  // delete an order from the order list

}
