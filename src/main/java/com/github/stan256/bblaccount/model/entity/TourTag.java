package com.github.stan256.bblaccount.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@NoArgsConstructor
@Entity(name = "tour_tags")
public class TourTag {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tour_tag_id")
    @SequenceGenerator(name = "tour_tag_id", sequenceName = "tour_tag_id_seq", initialValue = 200, allocationSize = 1)
    private Long id;

    @NaturalId
    @Column(name = "tag", unique = true, nullable = false)
    @Size(max = 100)
    private String tag;

    public TourTag(String tag){
        this.tag = tag;
    }
}
