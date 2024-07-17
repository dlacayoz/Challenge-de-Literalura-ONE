package com.ONE.literAlura.modelo;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") String fechaNacimiento,
        @JsonAlias("death_year") String fechaDeceso
) {
}
