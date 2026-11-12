/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hn.uth.poo.biblioteca.dao;

/**
 *
 * @author liz
 */

import java.util.List;

public interface CrudDAO<T, ID> {

    void insertar(T entidad);

    T buscarPorId(ID id);

    List<T> listar();

    boolean actualizar(T entidad);

    boolean eliminar(ID id);
}
