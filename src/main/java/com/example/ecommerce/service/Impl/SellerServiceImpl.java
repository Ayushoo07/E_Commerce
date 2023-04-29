package com.example.ecommerce.service.Impl;

import com.example.ecommerce.dto.RequestDto.AddSellerRequestDto;
import com.example.ecommerce.dto.RequestDto.GetSellerEmailRequest;import com.example.ecommerce.dto.RequestDto.UpdateSellerRequest;
import com.example.ecommerce.dto.ResponseDto.SellerResponseDto;
import com.example.ecommerce.exception.EmailAlreadyPresentException;
import com.example.ecommerce.exception.InvalidEmail;import com.example.ecommerce.model.Seller;
import com.example.ecommerce.repository.SellerRepository;
import com.example.ecommerce.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.stereotype.Service;import java.util.ArrayList;import java.util.List;

@Service
public class SellerServiceImpl {

    @Autowired
    SellerRepository sellerRepository;

    public SellerResponseDto addSeller(AddSellerRequestDto addSellerRequestDto) throws EmailAlreadyPresentException {

//        Seller seller = new Seller();
//        seller.setName(sellerRequestDto.getName());
//        seller.setEmailId(sellerRequestDto.getEmailId());
//        seller.setMobNo(sellerRequestDto.getMobNo());
//        seller.setAge(sellerRequestDto.getAge());

         if(sellerRepository.findByEmailId(addSellerRequestDto.getEmailId())!=null)
             throw new EmailAlreadyPresentException("Email Id already registered");

        Seller seller = SellerTransformer.SellerRequestDtoToSeller(addSellerRequestDto);
        Seller savedSeller = sellerRepository.save(seller);

       // prepare response Dto
        SellerResponseDto sellerResponseDto = SellerTransformer.SellerToSellerResponseDto(savedSeller);
        return sellerResponseDto;

    }
    public SellerResponseDto getSellerbyEmail(String email)throws InvalidEmail
    {
        Seller seller = sellerRepository.findByEmailId(email);

        if(seller==null)
        {
            throw new InvalidEmail("Invalid Email");
        }


        SellerResponseDto sellerResponseDto=SellerTransformer.SellerToSellerResponseDto(seller);

        return  sellerResponseDto;
    }

    public List<SellerResponseDto> allSeller()
    {
        List<Seller> sellers=sellerRepository.findAll();

        List<SellerResponseDto> res=new ArrayList<>();
        for(Seller seller:sellers)
        {
            SellerResponseDto obj=SellerTransformer.SellerToSellerResponseDto(seller);
            res.add(obj);
        }
        return res;

    }

    public ResponseEntity updateSellerByEmail(UpdateSellerRequest updateSellerRequest)throws InvalidEmail
    {
        Seller seller;
        try {
            seller=sellerRepository.findByEmailId(updateSellerRequest.getEmail());
        }
        catch (Exception e)
        {
            throw new InvalidEmail("Email does Not Exist");
        }

        if (updateSellerRequest.getName()!=null)
            seller.setName(updateSellerRequest.getName());
        if(updateSellerRequest.getAge()!=0)
            seller.setAge(updateSellerRequest.getAge());
        if(updateSellerRequest.getMobNo()!=null)
            seller.setMobNo(updateSellerRequest.getMobNo());

        sellerRepository.save(seller);

        return new ResponseEntity("Seller updated Succesfully",HttpStatus.OK);
    }


    public ResponseEntity deleteSellerByEmail(GetSellerEmailRequest getSellerEmailRequest)throws InvalidEmail
    {
        Seller seller;
        try {
            seller=sellerRepository.findByEmailId(getSellerEmailRequest.getEmailId());
        }
        catch (Exception e)
        {
            throw new InvalidEmail("Email does Not Exist");
        }

        sellerRepository.delete(seller);

        return new ResponseEntity("Seller deleted Successfully",HttpStatus.OK);
    }

    public List<SellerResponseDto> getSellersOfAge(Integer age)
    {
        List<Seller> sellers=sellerRepository.getSellersOfAge(age);

        List<SellerResponseDto> sellerResponseDtoList=new ArrayList<>();

        for(Seller seller:sellers)
        {
            SellerResponseDto obj=SellerTransformer.SellerToSellerResponseDto(seller);
            sellerResponseDtoList.add(obj);
        }
        return sellerResponseDtoList;

    }
}
