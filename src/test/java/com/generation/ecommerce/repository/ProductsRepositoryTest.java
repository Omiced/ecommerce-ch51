package com.generation.ecommerce.repository;
import com.generation.ecommerce.model.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProductsRepositoryTest {
    @Autowired
    private ProductsRepository productsRepository;

    @DisplayName("Test para probar que se guarda un product exitosamente")
    @Test
    public void onSaveProduct(){
        Products product = new Products();
        product.setName("reloj nivada");
        product.setQuantity(1);
        product.setDescription("Reloj azul de marca nivada");

        Products savedProduct = productsRepository.save(product);
        assertThat(savedProduct).isNotNull();
    }

    @DisplayName("Test para probar que recibimos todos los productos al hacer getAll")
    @Test
    public void onGetAll(){
        Products product = new Products();
        product.setName("reloj nivada");
        product.setQuantity(1);
        product.setDescription("Reloj azul de marca nivada");
        Products product2 = new Products();
        product2.setName("case iphone");
        product2.setQuantity(1);
        product2.setDescription("case iphone morada");
        Products product3 = new Products();
        product3.setName("pinzas de corte");
        product3.setQuantity(1);
        product3.setDescription("pinzas de corte negras");

        productsRepository.save(product);
        productsRepository.save(product2);
        productsRepository.save(product3);

        List<Products> allProducts = productsRepository.findAll();
        assertThat(allProducts.size()).isEqualTo(3);
    }

    @DisplayName("Test para probar la obtencion de un producto por su id")
    @Test
    public void onGetProductById(){
        Products product = new Products();
        product.setName("reloj nivada");
        product.setQuantity(1);
        product.setDescription("Reloj azul de marca nivada");

        productsRepository.save(product);
        Optional<Products> savedProduct = productsRepository.findById(product.getId());

        assertThat(savedProduct).isPresent()
                .hasValueSatisfying( productDB -> {
                    assertThat(productDB.getId()).isEqualTo(1L);
                    assertThat(productDB.getName()).isEqualTo("reloj nivada");
                    assertThat(productDB.getQuantity()).isEqualTo(1);
                    assertThat(productDB.getDescription()).isEqualTo("Reloj azul de marca nivada");
                });
    }

    @DisplayName("Test para probar que los datos de un product se actualizan")
    @Test
    public void onUpdateProductById(){
        Products product = new Products();
        product.setName("reloj nivada");
        product.setQuantity(1);
        product.setDescription("Reloj azul de marca nivada");

        productsRepository.save(product);
        Products savedProduct = productsRepository.findById(product.getId()).get();

        savedProduct.setName("reloj cassio");
        savedProduct.setQuantity(3);

        Products updatedProduct = productsRepository.save(savedProduct);

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).as("El nombre del producto deberia ser relo cassio").isEqualTo("reloj cassio");
        assertThat(updatedProduct.getQuantity()).as("La cantidad del producto deberia ser 3").isEqualTo(3);
    }

    @DisplayName("Test para probar que un producto es borrado de manera exitosa")
    @Test
    public void onDeleteProductById(){
        Products product = new Products();
        product.setName("reloj nivada");
        product.setQuantity(1);
        product.setDescription("Reloj azul de marca nivada");

        productsRepository.save(product);

        productsRepository.deleteById(product.getId());

        Optional<Products> deletedProduct = productsRepository.findById(product.getId());

        assertThat(deletedProduct)
                .as("El producto opcional deberia estar vacio porque ya fue borrado")
                .isEmpty();
    }




























}
