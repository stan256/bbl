package com.github.stan256.springtest.sql.model.postgres;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    private int id;
    private String productName;
    private int price;
    private Date lastUpdate;
    private boolean test;
    @Column()
    private Category category;
}
