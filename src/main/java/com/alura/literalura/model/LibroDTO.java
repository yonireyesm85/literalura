package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {
    private Long id;
    private String title;
    private List<AutorDTO> authors;
    private List<String> languages;
    private Integer download_count;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<AutorDTO> getAuthors() {
        return authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownload_count() {
        return download_count;
    }
}
