package com.application.BitHub.controller;

import java.util.*;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.application.BitHub.objects.User;
import com.application.BitHub.objects.Product;
import com.application.BitHub.repository.ProductRepository;
import com.application.BitHub.repository.UserRepository;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {
    Integer a = 0;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    // This method returns all the products in the database
    /*
     * GET /products/all
     * Returns all the products in the database
     *
     * @return List<Product> - List of all the products in the database
     *
     * @throws 404 - If there is an error in the database
     *
     * @Throws 204 - If there are no products in the database
     *
     */
    @GetMapping("/all")
    public ResponseEntity<Object> getAllProducts(@RequestParam(required = false) String name) {

        try {
            List<Product> products = productRepository.findAll();
            List<Product> availableProducts = new ArrayList<>();
            for(Product product : products ){
                if(product.getisAvailable()){
                    availableProducts.add(product);
                }
            }

            if (products.isEmpty()) {
                return new ResponseEntity<>("[]", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(availableProducts, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // This method returns all products by seller id
    /*
     * GET /products/id/{id}
     * Returns all products by seller id
     *
     * @param id - The id of the product
     *
     * @return Product - The product with the given id
     *
     * @throws 404 - If there is an error in the database
     *
     */
    @GetMapping("/id/{seller}")
    public ResponseEntity<Object> getProductById(@PathVariable("seller") long seller) {
        List<Product> productData = productRepository.findBySeller(seller);

        if (!productData.isEmpty()) {
            return new ResponseEntity<>(productData, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }
    }

    // Contextual Search
    /*
     * GET /products/name/{name}
     * Returns a product by name
     *
     * @param name - The name of the product
     *
     * @return Product - The product with the given name
     *
     * @throws 204 - If there is an error in the database
     *
     */
    @GetMapping("/search/{name}")
    public ResponseEntity<Object> findByNameContaining(@PathVariable String name) {
        try {
            List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
            List<Product> everyProduct = productRepository.findAll();
            List<Product> filteredProducts = new ArrayList<>();
            for (Product product : everyProduct) {
                if (product.getDescription().contains(name) && !(products.contains(product))) {
                    filteredProducts.add(product);
                }
            }
            products.addAll(filteredProducts);

            if (products.isEmpty()) {
                return new ResponseEntity<>("[]", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // This method places a product bid
    /*
     * POST /products/edit/{id}
     * Edits a product bid by id
     *
     * @param id - The id of the product
     *
     * @RequestBody
     * - name (optional)
     * - description (optional)
     * - price (optional)
     *
     * @return Product - The product with new bidder info
     *
     * @throws 404 - If there is an error in the database
     *
     */
    @PostMapping("/placebid/{id}")
    public ResponseEntity<Product> updateProductBid(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent() && productData.get().getisAvailable()) {
            Product tempProduct = productData.get();
            if(product.getPrice() > productData.get().getPrice()) {
                tempProduct.setBuyer(product.getBuyer());
                tempProduct.setPrice(product.getPrice());
            }
            else{
                return new ResponseEntity<>(tempProduct, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(productRepository.save(tempProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a product by id
    /*
     * POST /products/delete/{id}
     * Deletes a product by id
     *
     * @param id - The id of the product
     *
     * Returns 200 - If the product is deleted
     *
     * Returns 500 - If there is an error in the database or the product does not
     * exist
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {

        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product _product = productRepository.save(new Product(product.getName(), product.getBasePrice(),
                    product.getDescription(), product.getImage(),product.getSeller(), Integer.toUnsignedLong(a),
                    product.getBasePrice(), true
            ));
            return new ResponseEntity<>(_product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/freezebid/{id}")
    public ResponseEntity<Product> updateisAvailable(@PathVariable("id") long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product tempProduct = productData.get();
                tempProduct.setisAvailable(false);
            return new ResponseEntity<>(productRepository.save(tempProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
