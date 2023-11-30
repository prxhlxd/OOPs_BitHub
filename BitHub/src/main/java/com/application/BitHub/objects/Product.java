package com.application.BitHub.objects;


import javax.persistence.*;
import java.util.*;
import java.util.Vector;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private double price;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "seller")
    private Long seller;
    @Column(name = "buyer")
    private Long buyer;
    @Column(name = "basePrice")
    private Long basePrice;

    @Column(name = "isAvailable")
    private boolean isAvailable;
    public Product() {
    }

    public Product(String name, double price, String description, String image, Long seller, Long buyer, Long basePrice, boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.seller = seller;
        this.buyer = buyer;
        this.basePrice = basePrice;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }
    public Long getSeller() {
        return seller;
    }
    public void setSeller(Long seller) {
        this.seller = seller;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Long getBuyer() {
        return buyer;
    }
    public void setBuyer(Long buyer) {
        this.buyer = buyer;
    }
    public Long getBasePrice() {
        return basePrice;
    }
    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }

    public boolean getisAvailable() {
        return isAvailable;
    }
    public void setisAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
