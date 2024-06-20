package com.rahat.sales_management.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private String name;

    @JsonProperty(value = "mobile_no")
    @Column(name = "mobile_no")
    private String mobileNo;

    @JsonProperty(value = "email")
    @Column(name = "email")
    private String email;

    @JsonProperty(value = "address")
    @Column(name = "address")
    private String address;
}
