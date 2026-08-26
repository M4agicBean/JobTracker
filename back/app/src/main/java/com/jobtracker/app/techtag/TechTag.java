package com.jobtracker.app.techtag;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "tech_tag")
@Getter
public class TechTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    protected TechTag() {}

    public TechTag(String name) {
        this.name = name;
    }


}
