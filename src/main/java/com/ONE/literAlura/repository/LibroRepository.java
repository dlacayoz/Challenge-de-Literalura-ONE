package com.ONE.literAlura.repository;

import com.ONE.literAlura.modelo.Idioma;
import com.ONE.literAlura.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(Idioma idioma);
}
