package DAO;

import Model.Vehiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private Connection connection;

    public VehiculoDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean registrarVehiculo(Vehiculo vehiculo) throws SQLException {
        int idTipo = obtenerIdTipo(vehiculo.getTipo());
        int idPropietario = obtenerIdPropietario(vehiculo.getPropietario());

        if (idTipo == -1) {
            throw new SQLException("El tipo de vehículo '" + vehiculo.getTipo() + "' no existe en la BD.");
        }
        if (idPropietario == -1) {
            throw new SQLException("El propietario '" + vehiculo.getPropietario() + "' no está registrado.");
        }

        String sql = "INSERT INTO Vehiculo (placa, id_tipo, id_propietario, marca, modelo) VALUES (?, ?, ?, NULL, NULL)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getPlaca());
            ps.setInt(2, idTipo);
            ps.setInt(3, idPropietario);

            return ps.executeUpdate() > 0;
        }
    }

    public Vehiculo buscarPorPlaca(String placa) throws SQLException {
        String sql = "SELECT v.placa, t.descripcion as nombre_tipo, p.nombre as nombre_propietario " +
                "FROM Vehiculo v " +
                "JOIN Tipo t ON v.id_tipo = t.id_tipo " +
                "JOIN Propietario p ON v.id_propietario = p.id_propietario " +
                "WHERE v.placa = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetAVehiculo(rs);
                }
            }
        }
        return null;
    }

    public List<Vehiculo> listarVehiculos() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT v.placa, t.descripcion as nombre_tipo, p.nombre as nombre_propietario " +
                "FROM Vehiculo v " +
                "JOIN Tipo t ON v.id_tipo = t.id_tipo " +
                "JOIN Propietario p ON v.id_propietario = p.id_propietario";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearResultSetAVehiculo(rs));
            }
        }
        return lista;
    }

    public boolean eliminarVehiculo(String placa) throws SQLException {
        String sql = "DELETE FROM Vehiculo WHERE placa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, placa);
            return ps.executeUpdate() > 0;
        }
    }

    private Vehiculo mapearResultSetAVehiculo(ResultSet rs) throws SQLException {
        return new Vehiculo.Builder()
                .conPlaca(rs.getString("placa"))
                .deTipo(rs.getString("nombre_tipo"))
                .delPropietario(rs.getString("nombre_propietario"))
                .build();
    }

    private int obtenerIdTipo(String descripcion) throws SQLException {
        String sql = "SELECT id_tipo FROM Tipo WHERE descripcion = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_tipo");
            }
        }
        return -1;
    }

    private int obtenerIdPropietario(String nombrePropietario) throws SQLException {
        String sql = "SELECT id_propietario FROM Propietario WHERE nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombrePropietario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_propietario");
            }
        }
        return -1;
    }
}