package com.ONE.literAlura.repository;

import com.ONE.literAlura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fechaDeMuerte AND a.fechaDeceso >= :fechaDeMuerte")
    List<Autor> autoresPorFechaDeMuerte(int fechaDeMuerte);
}
