package com.alura.literalura.service;

import com.alura.literalura.model.Libro;
import com.alura.literalura.model.LibroDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class ConversorLibro {
    private final ObjectMapper mapper = new ObjectMapper();

    public LibroDTO convertirJsonEnLibroDesdeResultados(String json) {
        try {
            JsonNode raiz = mapper.readTree(json);
            JsonNode primerLibro = raiz.get("results").get(0);
            return mapper.treeToValue(primerLibro, LibroDTO.class);
        } catch (Exception e) {
            System.out.println("Error al convertir JSON en Libro! 😈" + e.getMessage());
            return null;
        }
    }

    public List<LibroDTO> convertirJsonEnListaDeLibros(String json) {
        ObjectMapper mapper = new ObjectMapper();
        List<LibroDTO> libros = new ArrayList<>();

        try {
            JsonNode raiz = mapper.readTree(json);
            JsonNode resultados = raiz.get("results");

            for (JsonNode nodo : resultados) {
                LibroDTO libro = mapper.treeToValue(nodo, LibroDTO.class);
                libros.add(libro);
            }
        } catch (Exception e) {
            System.out.println("❌ Error al convertir lista de libros: " + e.getMessage());
        }

        return libros;
    }

}
