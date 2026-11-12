/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author liz
 */

import hn.uth.poo.biblioteca.modelo.Libro;
import java.util.List;

public interface LibroDAO extends CrudDAO<Libro, Integer> {

    List<Libro> listarPorAutor(int autorId);

    Libro buscarPorIsbn(String isbn);
}


