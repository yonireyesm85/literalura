package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDTO {
    private String name;
    private Integer birth_year;
    private Integer death_year;

    public String getName() {
        return name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }
}
