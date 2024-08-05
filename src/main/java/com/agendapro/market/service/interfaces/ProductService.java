package com.agendapro.market.service.interfaces;


import com.agendapro.market.dto.ApiResponse;
import com.agendapro.market.dto.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto findById(String productId);
    List<ProductDto> findAllProducts();
    List<ProductDto> findAllProductsByName(String name);
    ApiResponse delete(String productId);
    ApiResponse save(ProductDto dto);
    ApiResponse update(String productId, ProductDto dto);
}