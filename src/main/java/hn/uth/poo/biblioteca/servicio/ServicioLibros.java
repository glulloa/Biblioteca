/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.servicio;

/**
 *
 * @author liz
 */

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.LibroDAO;
import hn.uth.poo.biblioteca.excepciones.DatoInvalidoException;
import hn.uth.poo.biblioteca.modelo.Autor;
import hn.uth.poo.biblioteca.modelo.Libro;

import java.time.Year;
import java.util.List;

public class ServicioLibros {

    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;

    public ServicioLibros(LibroDAO libroDAO, AutorDAO autorDAO) {
        this.libroDAO = libroDAO;
        this.autorDAO = autorDAO;
    }

    public void crear(Libro libro) {
        validarDatos(libro);
        validarISBNUnico(libro.getIsbn(), 0);
        validarAutor(libro.getAutor());

        libro.setIsbn(libro.getIsbn().trim());
        libro.setTitulo(libro.getTitulo().trim());

        libroDAO.insertar(libro);
    }

    public Libro buscarPorId(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID del libro debe ser mayor que cero.");
        }

        return libroDAO.buscarPorId(id);
    }

    public List<Libro> listar() {
        return libroDAO.listar();
    }

    public List<Libro> listarPorAutor(int autorId) {
        if (autorId <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor debe ser mayor que cero.");
        }

        if (autorDAO.buscarPorId(autorId) == null) {
            throw new DatoInvalidoException(
                    "El autor indicado no existe.");
        }

        return libroDAO.listarPorAutor(autorId);
    }

    public boolean actualizar(Libro libro) {
        validarDatos(libro);

        if (libro.getId() <= 0) {
            throw new DatoInvalidoException(
                    "El ID del libro no es válido.");
        }

        if (libroDAO.buscarPorId(libro.getId()) == null) {
            throw new DatoInvalidoException(
                    "El libro que intenta actualizar no existe.");
        }

        validarISBNUnico(libro.getIsbn(), libro.getId());
        validarAutor(libro.getAutor());

        libro.setIsbn(libro.getIsbn().trim());
        libro.setTitulo(libro.getTitulo().trim());

        return libroDAO.actualizar(libro);
    }

    public boolean eliminar(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID del libro debe ser mayor que cero.");
        }

        if (libroDAO.buscarPorId(id) == null) {
            throw new DatoInvalidoException(
                    "El libro no existe.");
        }

        return libroDAO.eliminar(id);
    }

    private void validarDatos(Libro libro) {
        if (libro == null) {
            throw new DatoInvalidoException(
                    "Los datos del libro son obligatorios.");
        }

        if (libro.getIsbn() == null
                || libro.getIsbn().trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El ISBN es obligatorio.");
        }

        if (libro.getTitulo() == null
                || libro.getTitulo().trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El título del libro es obligatorio.");
        }

        int anioActual = Year.now().getValue();

        if (libro.getAnio() < 1450
                || libro.getAnio() > anioActual) {
            throw new DatoInvalidoException(
                    "El año debe estar entre 1450 y " + anioActual + ".");
        }

        if (!Double.isFinite(libro.getPrecio())
                || libro.getPrecio() < 0) {
            throw new DatoInvalidoException(
                    "El precio debe ser mayor o igual a cero.");
        }
    }

    private void validarISBNUnico(String isbn, int idActual) {
        Libro existente = libroDAO.buscarPorIsbn(isbn.trim());

        if (existente != null && existente.getId() != idActual) {
            throw new DatoInvalidoException(
                    "Ya existe un libro registrado con ese ISBN.");
        }
    }

    private void validarAutor(Autor autor) {
        if (autor == null || autor.getId() <= 0) {
            throw new DatoInvalidoException(
                    "Debe seleccionar un autor válido.");
        }

        if (autorDAO.buscarPorId(autor.getId()) == null) {
            throw new DatoInvalidoException(
                    "No se puede registrar el libro porque el autor no existe.");
        }
    }
}

