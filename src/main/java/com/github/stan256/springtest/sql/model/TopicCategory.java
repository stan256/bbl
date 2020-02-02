package com.github.stan256.springtest.sql.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class TopicCategory {
    @Id
    private Long id;
    private String title;
}
