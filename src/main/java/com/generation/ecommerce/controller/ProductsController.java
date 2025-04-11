package com.generation.ecommerce.controller;

import com.generation.ecommerce.model.Products;
import com.generation.ecommerce.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products/")//http://localhost:8080/api/products/
public class ProductsController {
    private final ProductsService productsService;
    @Autowired
    public ProductsController(ProductsService productsService){
        this.productsService = productsService;
    }

    @GetMapping //http://localhost:8080/api/products/ con metodo get
    public List<Products> getAllProducts(){
        return productsService.getAllProducts();
    }

    @GetMapping(path="{productId}")//http://localhost:8080/api/products/productId con metodo get
    public Products getProductById(@PathVariable("productId")Long id){
        return productsService.getProductById(id);
    }

    @PostMapping //http://localhost:8080/api/products/ con metodo post
    public Products addProduct(@RequestBody Products product){
        return productsService.addProduct(product);
    }

    @DeleteMapping(path="{productId}")// http://localhost:8080/api/products/2 con metodo delete
    public Products deleteProductById(@PathVariable("productId")Long id){
        return productsService.deleteProductById(id);
    }

    @PutMapping(path="{productId}") //http://localhost:8080/api/products/2 con metodo put
    public Products updateProductById
            (@PathVariable("productId")Long id,
             @RequestBody Products product){
        return productsService.updateProduct(id,product);
    }



}
