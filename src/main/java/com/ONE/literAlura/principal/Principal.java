package com.ONE.literAlura.principal;

import com.ONE.literAlura.modelo.*;
import com.ONE.literAlura.repository.AutorRepository;
import com.ONE.literAlura.repository.LibroRepository;
import com.ONE.literAlura.service.ConsumoAPI;
import com.ONE.literAlura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books/";
    private Scanner keyboard = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    **************************
                    Elija la opcion a traves de su numero:
                    
                    1 - Buscar libro por titulo
                    2 - Listar libros reguistrados
                    3 - Litar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                                  
                    0 - Salir
                    **************************
                    """;
            System.out.println(menu);
            opcion = keyboard.nextInt();
            keyboard.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivos();
                    break;
                case 5:
                    mostrarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private DatosBiblioteca getDatosLibro() {
        System.out.println("Ingrese el titulo del libro que desea buscar: ");
        var tituloLibro = keyboard.nextLine();
        var newURL = this.createURL(tituloLibro);
        var dataBook = consumoAPI.obtenerDatos(newURL);
        DatosBiblioteca datos = convierteDatos.obtenerDatos(dataBook, DatosBiblioteca.class);
        return datos;

    }

    private void buscarLibro() {
        // obtener los datos del libro buscado
        Optional<DatosLibro> datos = getDatosLibro().resultado()
                .stream()
                .findFirst();

        if (datos.isPresent()) {
            // berificar si el libro ya existe por medio del titulo
            var nuevoLibro = datos.get();
            var nuevoTitulo = nuevoLibro.titulo();
            var nuevoAutor = nuevoLibro.autores().get(0).nombre();
            Boolean libroYaExiste = false;
            Boolean autorYaExiste = false;

            // mostrar libros registrados
            libros = repositorioLibro.findAll();

            for (int i = 0; i < libros.size(); i++) {
                if (libros.get(i).getTitulo().equalsIgnoreCase(nuevoTitulo)) {
                    libroYaExiste = true;
                }
            }

            if (!libroYaExiste) {
                // verificar si es un autor registrado
                autores = repositorioAutor.findAll();

                Autor newAuthor = null;

                for (int i = 0; i < autores.size() ; i++) {

                    if (autores.get(i).getNombre().equalsIgnoreCase(nuevoAutor)) {
                        autorYaExiste = true;
                        newAuthor = autores.get(i);
                    }
                }

                if (autorYaExiste) {
                    // guardando el autor ya existente con su nuevo libro
                    List<Libro> libros = newAuthor.getLibros();
                    Libro newBook = new Libro(nuevoLibro);
                    libros.add(newBook);
                    newAuthor.setLibros(libros);
                    repositorioAutor.save(newAuthor);

                } else {
                    // guardando un nuevo libro
                    Libro libro = new Libro(nuevoLibro);
                    Autor autor = new Autor(nuevoLibro.autores().get(0));
                    repositorioAutor.save(autor);
                    libro.setAutores(autor);
                    repositorioLibro.save(libro);
                    System.out.println("**********************************");
                    System.out.println("Libro agregado satisfactoriamente!");
                    System.out.println(libro);
                }

            }
            else {
                System.out.println("No se puede agregar un libro ya existente!");
            }


        }

    }

    private void mostrarLibrosRegistrados() {
        libros = repositorioLibro.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getDescargas).reversed())
                .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados(){
        autores = repositorioAutor.findAll();

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre).reversed())
                .forEach(System.out::println);
    }

    private void mostrarAutoresVivos() {
        System.out.println("Ingrese la fecha limite: ");
        int fecha = keyboard.nextInt();
        System.out.println();
        List<Autor> autoresVivos = repositorioAutor.autoresPorFechaDeMuerte(fecha);

        if (!autoresVivos.isEmpty()) {
            autoresVivos.forEach(System.out::println);
        }
    }

    private void mostrarLibrosPorIdiomas() {
        System.out.println("""
                Ingrese el idioma para buscar los libros: 
                es - español
                en - ingles
                fr - frances
                pt - portugues
                """);
        var idioma = keyboard.nextLine();
        var idiomaEnum = Idioma.fromString(idioma);
        List<Libro> libroPorIdioma = repositorioLibro.findByIdioma(idiomaEnum);

        if (!libroPorIdioma.isEmpty()) {
            libroPorIdioma.forEach(System.out::println);
        }else {
            System.out.println("No hemos encontrado libros en ese idioma!");
        }
    }

    private String createURL(String userBook){
        return URL_BASE + "/?search=" + userBook.toLowerCase().replace(" ", "+");
    }
}
