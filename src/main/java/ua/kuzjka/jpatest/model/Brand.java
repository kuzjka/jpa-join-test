package ua.kuzjka.jpatest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    @SequenceGenerator(name = "default_gen", sequenceName = "default_sequence")
    private Integer id;

    private String name;

    public Brand() {
    }

    public Brand(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
