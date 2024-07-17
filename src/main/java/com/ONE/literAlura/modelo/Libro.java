package com.ONE.literAlura.modelo;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer descargas;

    public Libro(){}
    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.idioma = Idioma.fromString(datos.idiomas().get(0));
        this.descargas = datos.descargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutores() {
        return autor;
    }

    public void setAutores(Autor autor) {
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Idioma getIdiomas() {
        return idioma;
    }

    public void setIdiomas(Idioma idiomas) {
        this.idioma = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return """
                -------- LIBRO --------
                Titulo: %s
                Autor: %s
                Idioma: %s
                Numero de descargas: %d
                -----------------------
                """.formatted(titulo, autor.getNombre(), idioma, descargas);
    }
}
