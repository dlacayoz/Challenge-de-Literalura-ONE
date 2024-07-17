package com.ONE.literAlura.modelo;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaDeceso;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaDeceso = datosAutor.fechaDeceso();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaDeceso() {
        return fechaDeceso;
    }

    public void setFechaDeceso(String fechaDeceso) {
        this.fechaDeceso = fechaDeceso;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutores(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return """
                -------- AUTOR --------
                Autor: %s
                fecha de nacimiento: %s
                fecha de muerte: %s
                libros: %s
                -----------------------
                """.formatted(nombre, fechaNacimiento, fechaDeceso,
                libros.stream().map(Libro::getTitulo).collect(Collectors.toList()));
    }
}
