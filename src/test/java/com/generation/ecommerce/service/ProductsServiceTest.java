package com.generation.ecommerce.service;

import com.generation.ecommerce.model.Products;
import com.generation.ecommerce.repository.ProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {
    @Mock
    private ProductsRepository productsRepository;

    @InjectMocks
    private ProductsService productsService;

    private Products product;

    @BeforeEach
    public void setUp(){
        product = new Products();
        product.setId(1L);
        product.setName("logitech g305");
        product.setDescription("Mouse morado marca logitech");
        product.setQuantity(2);
    }

    @DisplayName("Test para checar que el metodo addProduct del servicio funciona")
    @Test
    public void onAddProduct(){
        given(productsRepository.save(product)).willReturn(product);
        Products productAdded = productsService.addProduct(product);
        assertThat(productAdded).isNotNull();
    }





}
