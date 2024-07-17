package com.ONE.literAlura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBiblioteca(
        @JsonAlias("results") List<DatosLibro> resultado
) {
}
