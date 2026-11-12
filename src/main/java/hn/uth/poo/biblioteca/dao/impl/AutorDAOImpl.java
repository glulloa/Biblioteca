/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hn.uth.poo.biblioteca.dao.impl;

/**
 *
 * @author liz
 */

import hn.uth.poo.biblioteca.dao.AutorDAO;
import hn.uth.poo.biblioteca.dao.Conexion;
import hn.uth.poo.biblioteca.excepciones.DatosException;
import hn.uth.poo.biblioteca.modelo.Autor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutorDAOImpl implements AutorDAO {

    @Override
    public void insertar(Autor autor) {
        String sql = "INSERT INTO autores (nombre, nacionalidad) VALUES (?, ?)";

        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    autor.setId(rs.getInt(1));
                } else {
                    throw new DatosException(
                            "No se pudo obtener el ID del autor.");
                }
            }

        } catch (SQLException e) {
            throw new DatosException("Error al insertar autor", e);
        }
    }

    @Override
    public Autor buscarPorId(Integer id) {
        String sql = "SELECT id, nombre, nacionalidad "
                   + "FROM autores WHERE id = ?";

        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

            return null;

        } catch (SQLException e) {
            throw new DatosException("Error al buscar autor", e);
        }
    }

    @Override
    public List<Autor> listar() {
        List<Autor> autores = new ArrayList<>();

        String sql = "SELECT id, nombre, nacionalidad "
                   + "FROM autores ORDER BY nombre";

        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                autores.add(mapear(rs));
            }

            return autores;

        } catch (SQLException e) {
            throw new DatosException("Error al listar autores", e);
        }
    }

    @Override
    public boolean actualizar(Autor autor) {
        String sql = "UPDATE autores SET nombre = ?, "
                   + "nacionalidad = ? WHERE id = ?";

        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getNacionalidad());
            ps.setInt(3, autor.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatosException("Error al actualizar autor", e);
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        String sql = "DELETE FROM autores WHERE id = ?";

        try (Connection con = Conexion.abrir();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DatosException("Error al eliminar autor", e);
        }
    }

    private Autor mapear(ResultSet rs) throws SQLException {
        return new Autor(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("nacionalidad")
        );
    }
}
