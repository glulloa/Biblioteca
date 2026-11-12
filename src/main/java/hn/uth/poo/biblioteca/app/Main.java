/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.app;

/**
 *
 * @author liz
 */

import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.dao.impl.AutorDAOImpl;
import hn.uth.poo.biblioteca.dao.impl.LibroDAOImpl;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;
import hn.uth.poo.biblioteca.servicio.ServicioAutores;
import hn.uth.poo.biblioteca.servicio.ServicioLibros;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static ServicioAutores servicioAutores;
    private static ServicioLibros servicioLibros;

    public static void main(String[] args) {

        try {
            Conexion.inicializarBD();

            AutorDAOImpl autorDAO = new AutorDAOImpl();
            LibroDAOImpl libroDAO = new LibroDAOImpl();

            servicioAutores = new ServicioAutores(autorDAO, libroDAO);
            servicioLibros = new ServicioLibros(libroDAO, autorDAO);

            System.out.println("Base de datos conectada correctamente.");

            menuPrincipal();

        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: "
                    + e.getMessage());
        } catch (DatosException e) {
            System.out.println("Error de datos: " + e.getMessage());
        }
    }

    private static void menuPrincipal() {

        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       SISTEMA BIBLIOTECA");
            System.out.println("================================");
            System.out.println("1. Gestionar autores");
            System.out.println("2. Gestionar libros");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuAutores();
                case 2 -> menuLibros();
                case 0 -> System.out.println("Gracias por utilizar el sistema.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void menuAutores() {

        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("       GESTIÓN DE AUTORES");
            System.out.println("================================");
            System.out.println("1. Crear autor");
            System.out.println("2. Listar autores");
            System.out.println("3. Buscar autor por ID");
            System.out.println("4. Actualizar autor");
            System.out.println("5. Eliminar autor");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> ejecutar(Main::crearAutor);
                case 2 -> ejecutar(Main::listarAutores);
                case 3 -> ejecutar(Main::buscarAutor);
                case 4 -> ejecutar(Main::actualizarAutor);
                case 5 -> ejecutar(Main::eliminarAutor);
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void menuLibros() {

        int opcion;

        do {
            System.out.println("\n================================");
            System.out.println("        GESTIÓN DE LIBROS");
            System.out.println("================================");
            System.out.println("1. Crear libro");
            System.out.println("2. Listar libros");
            System.out.println("3. Buscar libro por ID");
            System.out.println("4. Actualizar libro");
            System.out.println("5. Eliminar libro");
            System.out.println("6. Listar libros de un autor");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> ejecutar(Main::crearLibro);
                case 2 -> ejecutar(Main::listarLibros);
                case 3 -> ejecutar(Main::buscarLibro);
                case 4 -> ejecutar(Main::actualizarLibro);
                case 5 -> ejecutar(Main::eliminarLibro);
                case 6 -> ejecutar(Main::listarLibrosPorAutor);
                case 0 -> System.out.println("Regresando al menú principal...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void crearAutor() {

        System.out.println("\n--- CREAR AUTOR ---");

        String nombre = leerTexto("Nombre: ");
        String nacionalidad = leerTextoOpcional("Nacionalidad: ");

        Autor autor = new Autor(nombre, nacionalidad);

        servicioAutores.crear(autor);

        System.out.println("Autor registrado correctamente.");
        System.out.println("ID generado: " + autor.getId());
    }

    private static void listarAutores() {

        System.out.println("\n--- LISTA DE AUTORES ---");

        List<Autor> autores = servicioAutores.listar();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private static void buscarAutor() {

        System.out.println("\n--- BUSCAR AUTOR ---");

        int id = leerEntero("Ingrese el ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(id);

        if (autor == null) {
            System.out.println("No se encontró el autor.");
        } else {
            System.out.println(autor);
        }
    }

    private static void actualizarAutor() {

        System.out.println("\n--- ACTUALIZAR AUTOR ---");

        int id = leerEntero("ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(id);

        if (autor == null) {
            System.out.println("No se encontró el autor.");
            return;
        }

        System.out.println("Datos actuales: " + autor);

        String nombre = leerTexto("Nuevo nombre: ");
        String nacionalidad = leerTextoOpcional("Nueva nacionalidad: ");

        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);

        servicioAutores.actualizar(autor);

        System.out.println("Autor actualizado correctamente.");
    }

    private static void eliminarAutor() {

        System.out.println("\n--- ELIMINAR AUTOR ---");

        int id = leerEntero("ID del autor: ");

        boolean eliminado = servicioAutores.eliminar(id);

        if (eliminado) {
            System.out.println("Autor eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el autor.");
        }
    }

    private static void crearLibro() {

        System.out.println("\n--- CREAR LIBRO ---");

        System.out.println("Autores disponibles:");
        listarAutores();

        int autorId = leerEntero("ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(autorId);

        if (autor == null) {
            throw new DatoInvalidoException(
                    "El autor seleccionado no existe.");
        }

        String isbn = leerTexto("ISBN: ");
        String titulo = leerTexto("Título: ");
        int anio = leerEntero("Año de publicación: ");
        double precio = leerDouble("Precio: ");

        Libro libro = new Libro(
                isbn,
                titulo,
                anio,
                precio,
                autor
        );

        servicioLibros.crear(libro);

        System.out.println("Libro registrado correctamente.");
        System.out.println("ID generado: " + libro.getId());
    }

    private static void listarLibros() {

        System.out.println("\n--- LISTA DE LIBROS ---");

        List<Libro> libros = servicioLibros.listar();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private static void buscarLibro() {

        System.out.println("\n--- BUSCAR LIBRO ---");

        int id = leerEntero("Ingrese el ID del libro: ");

        Libro libro = servicioLibros.buscarPorId(id);

        if (libro == null) {
            System.out.println("No se encontró el libro.");
        } else {
            System.out.println(libro);
        }
    }

    private static void actualizarLibro() {

        System.out.println("\n--- ACTUALIZAR LIBRO ---");

        int id = leerEntero("ID del libro: ");

        Libro libro = servicioLibros.buscarPorId(id);

        if (libro == null) {
            System.out.println("No se encontró el libro.");
            return;
        }

        System.out.println("Datos actuales: " + libro);

        System.out.println("\nAutores disponibles:");
        listarAutores();

        int autorId = leerEntero("Nuevo ID del autor: ");

        Autor autor = servicioAutores.buscarPorId(autorId);

        if (autor == null) {
            throw new DatoInvalidoException(
                    "El autor seleccionado no existe.");
        }

        String isbn = leerTexto("Nuevo ISBN: ");
        String titulo = leerTexto("Nuevo título: ");
        int anio = leerEntero("Nuevo año: ");
        double precio = leerDouble("Nuevo precio: ");

        libro.setAutor(autor);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setPrecio(precio);

        servicioLibros.actualizar(libro);

        System.out.println("Libro actualizado correctamente.");
    }

    private static void eliminarLibro() {

        System.out.println("\n--- ELIMINAR LIBRO ---");

        int id = leerEntero("ID del libro: ");

        boolean eliminado = servicioLibros.eliminar(id);

        if (eliminado) {
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No se pudo eliminar el libro.");
        }
    }

    private static void listarLibrosPorAutor() {

        System.out.println("\n--- LIBROS POR AUTOR ---");

        listarAutores();

        int autorId = leerEntero("Ingrese el ID del autor: ");

        List<Libro> libros = servicioLibros.listarPorAutor(autorId);

        if (libros.isEmpty()) {
            System.out.println("Este autor no tiene libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    private static void ejecutar(Runnable operacion) {
        try {
            operacion.run();
        } catch (DatoInvalidoException e) {
            System.out.println("\nVALIDACIÓN: " + e.getMessage());
        } catch (DatosException e) {
            System.out.println("\nERROR DE DATOS: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("\nERROR: " + e.getMessage());
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static String leerTextoOpcional(String mensaje) {
        System.out.print(mensaje);
        String texto = scanner.nextLine();
        return texto.trim().isEmpty() ? null : texto;
    }

    private static int leerEntero() {
        return leerEntero(" ");
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(
                        scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número decimal válido.");
            }
        }
    }
}

