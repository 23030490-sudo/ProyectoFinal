package DAO;

import Model.Usuario;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        int idRol = obtenerIdRolPorNombre(usuario.getRol());
        if (idRol == -1) throw new SQLException("Rol no encontrado");


        String passwordHashed = encriptarSHA256(usuario.getPassword());

        String sql = "INSERT INTO Usuario (nombre, username, password_hash, id_rol) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, passwordHashed);
            ps.setInt(4, idRol);

            if (ps.executeUpdate() > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) usuario.setId(rs.getInt(1));
                }
                return true;
            }
        }
        return false;
    }


    public Usuario autenticar(String username, String passwordTextoPlano) throws SQLException {
        String sql = "SELECT u.id_usuario, u.nombre, u.username, u.password_hash, r.nombre as nombre_rol " +
                "FROM Usuario u " +
                "JOIN Rol r ON u.id_rol = r.id_rol " +
                "WHERE u.username = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            System.out.println("Buscando usuario: " + username); // DEBUG

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("password_hash");
                    String hashIntento = encriptarSHA256(passwordTextoPlano);

                    System.out.println("Hash en BD: " + hashGuardado); // DEBUG
                    System.out.println("Hash generado del input: " + hashIntento); // DEBUG

                    if (hashIntento.equals(hashGuardado)) {
                        return mapearResultSetAUsuario(rs);
                    } else {
                        System.out.println("Error: Las contraseñas no coinciden.");
                    }
                } else {
                    System.out.println("Error: Usuario no encontrado en la BD.");
                }
            }
        }
        return null;
    }


    private String encriptarSHA256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Usuario mapearResultSetAUsuario(ResultSet rs) throws SQLException {
        return new Usuario.Builder(rs.getString("username"), rs.getString("password_hash"))
                .conId(rs.getInt("id_usuario"))
                .conNombre(rs.getString("nombre"))
                .conRol(rs.getString("nombre_rol"))
                .build();
    }

    private int obtenerIdRolPorNombre(String nombreRol) throws SQLException {
        String sql = "SELECT id_rol FROM Rol WHERE nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombreRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_rol");
            }
        }
        return -1;
    }
}