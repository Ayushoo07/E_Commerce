package com.example.ecommerce.service.Impl;

import com.example.ecommerce.dto.RequestDto.OrderRequestDto;
import com.example.ecommerce.dto.ResponseDto.ItemResponseDto;
import com.example.ecommerce.dto.ResponseDto.OrderResponseDto;
import com.example.ecommerce.exception.InvalidCardException;
import com.example.ecommerce.exception.InvalidCustomerException;
import com.example.ecommerce.exception.InvalidProductException;
import com.example.ecommerce.model.*;
import com.example.ecommerce.repository.CardRespository;import com.example.ecommerce.repository.CustomerRepository;import com.example.ecommerce.repository.OrderedRepository;import com.example.ecommerce.repository.ProductRepository;import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.transformer.ItemTransformer;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired ProductServiceImpl productServiceImpl;

    @Autowired CustomerRepository customerRepository;

    @Autowired OrderedRepository orderedRepository;

    @Autowired ProductRepository productRepository;

    @Autowired CardRespository cardRespository;

    public Ordered placeOrder(Customer customer, Card card) throws Exception {

        Cart cart = customer.getCart();

        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));

        String maskedCardNo = generateMaskedCard(card.getCardNo());
        order.setCardUsed(maskedCardNo);
        order.setCustomer(customer);

        List<Item> orderedItems = new ArrayList<>();
        for(Item item: cart.getItems()){
            try{
                productServiceImpl.decreaseProductQuantity(item);
                orderedItems.add(item);
            } catch (Exception e) {
                throw new Exception("Product Out of stock");
            }
        }
        order.setItems(orderedItems);
        for(Item item: orderedItems)
            item.setOrder(order);
        order.setTotalValue(cart.getCartTotal());
        return order;
    }

  public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws Exception {

        Customer customer;
        try{
            customer = customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }
        catch (Exception e){
      throw new InvalidCustomerException("Customer Id is invalid !!");
        }

        Product product;
        try{
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch(Exception e){
      throw new InvalidProductException("Product doesn't exist");
        }

        Card card = cardRespository.findByCardNo(orderRequestDto.getCardNo());
        if(card==null || card.getCvv()!=orderRequestDto.getCvv() || card.getCustomer()!=customer){
      throw new InvalidCardException("Your card is not valid!!");
        }

        Item item = Item.builder()
                .requiredQuantity(orderRequestDto.getRequiredQuantity())
                .product(product)
                .build();
        try{
            productServiceImpl.decreaseProductQuantity(item);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Ordered order = new Ordered();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));
        String maskedCardNo = generateMaskedCard(card.getCardNo());
        order.setCardUsed(maskedCardNo);
        order.setCustomer(customer);
        order.setTotalValue(item.getRequiredQuantity()*product.getPrice());
        order.getItems().add(item);

        customer.getOrderList().add(order);
        item.setOrder(order);
        product.getItemList().add(item);

        Ordered savedOrder = orderedRepository.save(order); // order and item

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderDate(savedOrder.getOrderDate());
        orderResponseDto.setCardUsed(savedOrder.getCardUsed());
        orderResponseDto.setCustomerName(customer.getName());
        orderResponseDto.setOrderNo(savedOrder.getOrderNo());
        orderResponseDto.setTotalValue(savedOrder.getTotalValue());

    List<ItemResponseDto> items = new ArrayList<>();
        for(Item itemEntity: savedOrder.getItems()){
            ItemResponseDto itemResponseDto = ItemTransformer.ItemToItemResponseDto(itemEntity);
            items.add(itemResponseDto);
        }

        orderResponseDto.setItems(items);
        return orderResponseDto;

    }

  @Override
  public List<OrderResponseDto> getAllOrdersByEmail(String email)throws InvalidCustomerException {
    Customer customer = customerRepository.findByEmailId(email);
    if (customer == null) {
        throw new InvalidCustomerException("Customer Does Not Exist");
    }

    List<OrderResponseDto> orderResponseDtoList=new ArrayList<>();
    List<Ordered> orders=customer.getOrderList();

    for (Ordered order: orders)
    {
        OrderResponseDto orderResponseDto=new OrderResponseDto();
        orderResponseDto.setCustomerName(customer.getName());
        orderResponseDto.setOrderNo(order.getOrderNo());
        orderResponseDto.setOrderDate(order.getOrderDate());
        List<ItemResponseDto> items=new ArrayList<>();
        for(Item item:order.getItems())
        {
            items.add(ItemTransformer.ItemToItemResponseDto(item));
        }
        orderResponseDto.setItems(items);
        orderResponseDto.setCardUsed(order.getCardUsed());
        orderResponseDto.setTotalValue(order.getTotalValue());
        orderResponseDtoList.add(orderResponseDto);

    }

    return orderResponseDtoList;
  }

  @Override
  public List<OrderResponseDto> getrecentorders() {
    List<Ordered> orders=orderedRepository.orderByDate();

    List<OrderResponseDto> orderResponseDtoList=new ArrayList<>();

    int size=orders.size();
    int n=0;
    if(size<5)
    {
        n=size;
    }
    else
    {
        n=5;
    }

    for(int i=0;i<n;i++)
    {
        OrderResponseDto orderResponseDto=new OrderResponseDto();
        orderResponseDto.setCardUsed(orders.get(i).getCardUsed());
        orderResponseDto.setOrderNo(orders.get(i).getOrderNo());
        orderResponseDto.setOrderDate(orders.get(i).getOrderDate());
        orderResponseDto.setCustomerName(orders.get(i).getCustomer().getName());
        orderResponseDto.setTotalValue(orders.get(i).getTotalValue());
        List<ItemResponseDto> itemResponseDtoList=new ArrayList<>();
        for(Item item:orders.get(i).getItems())
        {
            ItemResponseDto itemResponseDto=ItemTransformer.ItemToItemResponseDto(item);
            itemResponseDtoList.add(itemResponseDto);
        }

        orderResponseDto.setItems(itemResponseDtoList);

        orderResponseDtoList.add(orderResponseDto);
    }


    return orderResponseDtoList;
  }

  @Override
  public OrderResponseDto getOrderWithHighestCost() {
    List<Ordered> orderedList=orderedRepository.findAll();

    int max=0;
    Ordered maximumValueOrder=new Ordered();

    for(Ordered order: orderedList)
    {
        if(order.getTotalValue()>max)
        {
            max=order.getTotalValue();
            maximumValueOrder = order;
        }
    }
    OrderResponseDto orderResponseDto=new OrderResponseDto();
      orderResponseDto.setCustomerName(maximumValueOrder.getCustomer().getName());
      orderResponseDto.setOrderNo(maximumValueOrder.getOrderNo());
      orderResponseDto.setOrderDate(maximumValueOrder.getOrderDate());
      List<ItemResponseDto> items=new ArrayList<>();
      for(Item item:maximumValueOrder.getItems())
      {
          items.add(ItemTransformer.ItemToItemResponseDto(item));
      }
      orderResponseDto.setItems(items);
      orderResponseDto.setCardUsed(maximumValueOrder.getCardUsed());
      orderResponseDto.setTotalValue(maximumValueOrder.getTotalValue());


    return orderResponseDto;
  }

    public String generateMaskedCard(String cardNo){
        String maskedCardNo = "";
        for(int i = 0;i<cardNo.length()-4;i++)
            maskedCardNo += 'X';
        maskedCardNo += cardNo.substring(cardNo.length()-4);
        return maskedCardNo;

    }
}
