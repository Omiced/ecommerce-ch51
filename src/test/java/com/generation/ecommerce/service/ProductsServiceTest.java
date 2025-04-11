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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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

    @DisplayName("Test para probar que el metodo getProductById del servicio funciona")
    @Test
    public void onGetProductById(){
        long productId = 1L;
        given(productsRepository.findById(productId)).willReturn(Optional.of(product));
        Products productSaved = productsService.getProductById(productId);
        assertThat(productSaved).isNotNull();
    }

    @DisplayName("Test para probar que el metodo deleteProductById del servcio funciona")
    @Test
    public void onDeleteProductById(){
        long productId = 1L;

        when(productsRepository.existsById(productId)).thenReturn(true);
        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));

        Products deletedProduct = productsService.deleteProductById(productId);

        assertNotNull(deletedProduct);
        assertEquals(productId,deletedProduct.getId());
        verify(productsRepository).existsById(productId);
        verify(productsRepository).findById(productId);
        verify(productsRepository).deleteById(productId);
    }

    @DisplayName("Test para probar que el metodo updateProductById del servcio funcion")
    @Test
    public void onUpdateProductById(){
        long productId = 1L;

        Products productDetails =  new Products();
        productDetails.setName("crema cerave");
        productDetails.setDescription("Crema para el cuerpo cerave sin aroma");
        productDetails.setQuantity(4);

        when(productsRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Products updatedProduct = productsService.updateProduct(productId, productDetails);

        assertEquals("crema cerave", updatedProduct.getName());
        assertEquals("Crema para el cuerpo cerave sin aroma", updatedProduct.getDescription());
        assertEquals(4, updatedProduct.getQuantity());

        verify(productsRepository).findById(productId);
        verify(productsRepository).save(product);
    }




}
