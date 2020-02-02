package com.github.stan256.springtest.sql.model.postgres;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue
    private int id;
    private String categoryName;
    private Date lastUpdate;
}
