package com.agendapro.market.service.implementation;

import com.agendapro.market.dto.ApiResponse;
import com.agendapro.market.dto.ProductDto;
import com.agendapro.market.entity.Product;
import com.agendapro.market.exception.GenericException;
import com.agendapro.market.exception.ProductNotFoundException;
import com.agendapro.market.mapper.ProductMapper;
import com.agendapro.market.repository.ProductRepository;
import com.agendapro.market.service.interfaces.ProductService;
import com.agendapro.market.shared.constant.Message;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository repository){
        this.productRepository = repository;
    }

    @Override
    public ProductDto findById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        Message.PRODUCT_NOT_FOUND,
                        HttpStatus.NOT_FOUND,
                        LocalDateTime.now())
                );
        return ProductMapper.INSTANCE.toProductDto(product);
    }

    @Override
    public List<ProductDto> findAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper.INSTANCE::toProductDto)
                .toList();
    }

    @Override
    public List<ProductDto> findAllProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);

        if (products.isEmpty()) {
            throw new ProductNotFoundException(
                    Message.PRODUCT_NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    LocalDateTime.now()
            );
        }

        return products.stream()
                .map(ProductMapper.INSTANCE::toProductDto)
                .toList();
    }

    @Override
    public ApiResponse delete(String productId) {
        ProductDto productDto = findById(productId);
        productRepository.deleteById(productDto.getId());
        return new ApiResponse(Message.PRODUCT_DELETE_SUCCESSFULLY, HttpStatus.OK);
    }


    @Override
    public ApiResponse save(ProductDto dto) {
        try {
            var currentDate = Instant.now();
            dto.setCreatedAt(currentDate);
            dto.setUpdatedAt(currentDate);

            Product product = ProductMapper.INSTANCE.toProduct(dto);
            productRepository.save(product);

            return new ApiResponse(Message.PRODUCT_SAVE_SUCCESSFULLY, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        }
    }

    @Override
    public ApiResponse update(String productId, ProductDto productRequest) {
        try {
            ProductDto productDto = findById(productId);

            if (Strings.isNotBlank(productRequest.getName())) {
                productDto.setName(productRequest.getName());
            }
            if (productRequest.getPrice() != null) {
                productDto.setPrice(productRequest.getPrice());
            }
            productDto.setUpdatedAt(Instant.now());

            productRepository.save(ProductMapper.INSTANCE.toProduct(productDto));

            return new ApiResponse(Message.PRODUCT_UPDATE_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        }
    }

}