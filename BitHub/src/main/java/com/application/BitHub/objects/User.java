package com.application.BitHub.objects;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "balance")
    private double balance;

    @Column(name = "hostel")
    private String hostel;
//    @OneToMany(mappedBy = "user")
//    private List<Order> orders;


    public User() {
    }

    public User(String name, String email, String password, String phone, String hostel) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.balance = 1000;
        this.hostel = hostel;
    }
    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String setPhone(String phone) {
        return this.phone = phone;
    }

    public boolean isBitsian(String email){
        String regex = "^f[0-9_+&.-]+@hyderabad+\\.bits\\-pilani\\.ac\\.in";
        return (email.matches(regex));
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getBalance() {
        return balance;
    }
}

/*
Products

 "name" : "Nilkamal Chair",
    "basePrice" : 2530,
    "description" : "Chair for your everday study and sitting life",
    "image" : "chair.png",
    "seller" : "3"


 */

