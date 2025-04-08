package com.generation.ecommerce.service;


import com.generation.ecommerce.model.Products;
import com.generation.ecommerce.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    private final ProductsRepository productsRepository;
    @Autowired
    public ProductsService(ProductsRepository productsRepository){
        this.productsRepository = productsRepository;
    }

    public List<Products> getAllProducts(){
        return productsRepository.findAll();
    }

    public Products getProductById(Long id){
        return productsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("El producto con el " + id + "no existe")
        );
    }

    public Products deleteProductById(Long id){
        Products tmp = null;
        if(productsRepository.existsById(id)){
            tmp = productsRepository.findById(id).get();
            productsRepository.deleteById(id);
            return tmp;
        }
        return tmp;
    }

    public Products addProduct(Products product){
        return productsRepository.save(product);
    }

    public Products updateProduct(Long id, Products productDetails){//product details es el producto a actualizar que recibimos del usuario
        Optional<Products> optionalProduct = productsRepository.findById(id);
        if(optionalProduct.isEmpty()) throw  new IllegalArgumentException("El producto con el " + id + "no existe");
        Products product = optionalProduct.get(); //product es el producto que vamos a actualizar con los valores obtenidos de produc details
        if(productDetails.getName() != null)product.setName(productDetails.getName());
        if(productDetails.getDescription() != null) product.setDescription(productDetails.getDescription());
        if(productDetails.getQuantity() != null) product.setQuantity(productDetails.getQuantity());
        return productsRepository.save(product);
    }




}
