package com.github.stan256.springtest.sql.model.postgres;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String secondName;
    private int age;
    private Date lastUpdate;
    private boolean test;
}
