package com.github.stan256.springtest.sql.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
public class Topic {
    @Id
    private Long id;
    private String title;

    @OneToOne
    private TopicCategory category;
}
