package com.agendapro.market.controller;

import com.agendapro.market.dto.ApiResponse;
import com.agendapro.market.dto.ProductDto;
import com.agendapro.market.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(service.findAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(
            @PathVariable(name = "productId") String productId
    ) {
        return ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> searchProduct(
            @PathVariable(name = "name") String name
    ) {
        return ResponseEntity.ok(service.findAllProductsByName(name));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createProduct(
            @RequestBody ProductDto productDto
    ) {
        return ResponseEntity.ok(service.save(productDto));
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable(name = "productId") String productId
    ) {
        return ResponseEntity.ok(service.delete(productId));
    }

   @PatchMapping("/{productId}")
   public ResponseEntity<ApiResponse> updateProduct(
           @PathVariable(name = "productId") String productId,
           @RequestBody ProductDto productDto
   ) {
        return ResponseEntity.ok(service.update(productId, productDto));
   }

}