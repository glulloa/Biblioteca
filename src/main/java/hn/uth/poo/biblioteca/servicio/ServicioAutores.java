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

import java.util.List;

public class ServicioAutores {

    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public ServicioAutores(AutorDAO autorDAO, LibroDAO libroDAO) {
        this.autorDAO = autorDAO;
        this.libroDAO = libroDAO;
    }

    public void crear(Autor autor) {
        validarNombre(autor.getNombre());
        autor.setNombre(autor.getNombre().trim());

        autorDAO.insertar(autor);
    }

    public Autor buscarPorId(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor debe ser mayor que cero.");
        }

        return autorDAO.buscarPorId(id);
    }

    public List<Autor> listar() {
        return autorDAO.listar();
    }

    public boolean actualizar(Autor autor) {
        validarNombre(autor.getNombre());

        if (autor.getId() <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor no es válido.");
        }

        autor.setNombre(autor.getNombre().trim());

        if (autorDAO.buscarPorId(autor.getId()) == null) {
            throw new DatoInvalidoException(
                    "El autor que intenta actualizar no existe.");
        }

        return autorDAO.actualizar(autor);
    }

    public boolean eliminar(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                    "El ID del autor debe ser mayor que cero.");
        }

        if (autorDAO.buscarPorId(id) == null) {
            throw new DatoInvalidoException(
                    "El autor no existe.");
        }

        if (!libroDAO.listarPorAutor(id).isEmpty()) {
            throw new DatoInvalidoException(
                    "No se puede eliminar el autor porque tiene libros registrados.");
        }

        return autorDAO.eliminar(id);
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException(
                    "El nombre del autor es obligatorio.");
        }
    }
}

