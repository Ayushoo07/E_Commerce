package com.example.ecommerce.controller;

import com.example.ecommerce.Enum.ProductCategory;
import com.example.ecommerce.dto.RequestDto.AddProductRequestDto;
import com.example.ecommerce.dto.RequestDto.GetSellerEmailRequest;import com.example.ecommerce.dto.RequestDto.GetSellerProductIdRequest;import com.example.ecommerce.dto.ResponseDto.ProductResponseDto;
import com.example.ecommerce.exception.InvalidSellerException;
import com.example.ecommerce.service.Impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productServiceImpl;

    @PostMapping("/add")
    public ProductResponseDto addProduct(@RequestBody AddProductRequestDto addProductRequestDto) throws InvalidSellerException {

        return productServiceImpl.addProduct(addProductRequestDto);
    }

    // get all products of a particular category
    @GetMapping("/get/{category}")
    public List<ProductResponseDto> getAllProductsByCategory(@PathVariable("category") ProductCategory category){
        return productServiceImpl.getAllProductsByCategory(category);
    }

    // Get all product by seller email id

    @GetMapping("/getProductsBySellerEmail")
    public ResponseEntity getProductsBySellerEmail(@RequestBody GetSellerEmailRequest getSellerEmailRequest)throws InvalidSellerException
    {
        List<ProductResponseDto> products=productServiceImpl.getProductsBySellerEmail(getSellerEmailRequest);

        return new ResponseEntity(products,HttpStatus.FOUND);
    }

    // delete a product by seller id and product id
    @DeleteMapping("/deleteProductBySellerProductId")
    public ResponseEntity deleteProductBySellerProductId(@RequestBody GetSellerProductIdRequest getSellerProductIdRequest)
    {
        productServiceImpl.deleteProductBySellerProductId(getSellerProductIdRequest);
        return new ResponseEntity("product deleted Succesfully",HttpStatus.OK);
    }


    // return top 5 cheapest products

    // return top 5 costliest products

    // return all out of stock products

    // return all available products

    // return all products that have quantity less than 10

    // return the cheapest product in a particular category

    // return the costliest product in a particular category
}
