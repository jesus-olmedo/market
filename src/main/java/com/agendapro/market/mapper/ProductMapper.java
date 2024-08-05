package com.agendapro.market.mapper;

import com.agendapro.market.dto.ProductDto;
import com.agendapro.market.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring"
)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto source);
    ProductDto toProductDto(Product source);
}
