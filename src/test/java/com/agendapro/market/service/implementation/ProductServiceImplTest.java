package com.agendapro.market.service.implementation;

import com.agendapro.market.dto.ApiResponse;
import com.agendapro.market.dto.ProductDto;
import com.agendapro.market.entity.Product;
import com.agendapro.market.repository.ProductRepository;
import com.agendapro.market.shared.constant.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    private Product productEntityMock;
    private Product productEntityExcpected;
    private ProductDto productDtoExpected;
    private ProductDto productDtoRequest;
    private final String productId = "123";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        this.productEntityMock = Product.builder()
                .name("pasta de tomate")
                .price(BigDecimal.valueOf(2.0))
                .build();

        this.productDtoExpected = ProductDto.builder()
                .name("pasta de tomate")
                .price(BigDecimal.valueOf(2.0))
                .build();

        this.productDtoRequest = ProductDto.builder()
                .name("leche vitapro")
                .price(BigDecimal.valueOf(1.5))
                .build();

        this.productEntityExcpected = Product.builder()
                .name("leche vitapro")
                .price(BigDecimal.valueOf(1.5))
                .build();
    }

    @Test
    void Given_findById_should_return_product_successfully() {

        given(this.productRepository.findById(anyString())).willReturn(Optional.ofNullable(this.productEntityMock));

        var response = this.productService.findById(productId);

        verify(this.productRepository, times(1)).findById(productId);
        assertEquals(this.productDtoExpected, response);
    }

    @Test
    void Given_findById_should_return_error_when_repository_fails() {

        doThrow(new DataAccessException("error"){}).when(this.productRepository).findById(anyString());

        try {
            this.productService.findById(productId);
        } catch (Exception e){
            assertEquals("error", e.getMessage());
        }

        verify(this.productRepository, times(1)).findById(productId);
    }

    @Test
    void Given_findAllProducts_should_return_products_successfully() {

        given(this.productRepository.findAll()).willReturn(List.of(this.productEntityMock));

        var response = this.productService.findAllProducts();

        verify(this.productRepository, times(1)).findAll();
        assertEquals(List.of(this.productDtoExpected), response);
    }

    @Test
    void Given_findAllProductsByName_should_return_products_successfully() {

        given(this.productRepository.findByNameContainingIgnoreCase(anyString())).willReturn(List.of(this.productEntityMock));

        String productName = "fakeProducct";
        var response = this.productService.findAllProductsByName(productName);

        verify(this.productRepository, times(1)).findByNameContainingIgnoreCase(productName);
        assertEquals(List.of(this.productDtoExpected), response);
    }

    @Test
    void Given_save_should_saved_successfully() {

        given(this.productRepository.save(any())).willReturn(productEntityExcpected);

        var response = this.productService.save(this.productDtoRequest);

        verify(this.productRepository, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Test
    void Given_save_should_return_error_when_repository_fails() {

        doThrow(new DataAccessException("error"){}).when(this.productRepository).save(any());

        try {
            this.productService.save(this.productDtoRequest);
        }catch (Exception e) {
            assertEquals("error", e.getMessage());
        }

        verify(this.productRepository, times(1)).save(any());
    }

    @Test
    void Given_update_should_updated_successfully() {

        given(this.productRepository.findById(anyString())).willReturn(Optional.ofNullable(productEntityExcpected));
        given(this.productRepository.save(any())).willReturn(productEntityExcpected);

        var response = this.productService.update(productId, this.productDtoRequest);

        verify(this.productRepository, times(1)).findById(any());
        verify(this.productRepository, times(1)).save(any());
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void Given_update_should_return_error_when_repository_fails() {

        given(this.productRepository.findById(anyString())).willReturn(Optional.ofNullable(productEntityExcpected));
        doThrow(new DataAccessException("error"){}).when(this.productRepository).save(any());

        try {
            this.productService.update(productId, this.productDtoRequest);
        } catch (Exception e) {
            assertEquals("error", e.getMessage());
        }

        verify(this.productRepository, times(1)).findById(any());
        verify(this.productRepository, times(1)).save(any());
    }

    @Test
    void Given_delete_should_deleted_successfully() {
        this.productEntityExcpected.setId("134");
        given(this.productRepository.findById(anyString())).willReturn(Optional.ofNullable(this.productEntityExcpected));
        doNothing().when(productRepository).deleteById(productId);

        var response = this.productService.delete(productId);

        verify(this.productRepository, times(1)).findById(any());
        verify(this.productRepository, times(1)).deleteById(any());
        assertEquals(HttpStatus.OK, response.getStatus());
    }
}