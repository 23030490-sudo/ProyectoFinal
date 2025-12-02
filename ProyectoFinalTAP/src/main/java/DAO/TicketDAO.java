package DAO;

import Model.Ticket;
import Model.Vehiculo;

import java.sql.*;
import java.time.LocalDateTime;

public class TicketDAO {

    private Connection connection;

    public TicketDAO(Connection connection) {
        this.connection = connection;
    }

    public void crearTicket(Ticket ticket, String tipoVehiculoTexto, String marca, String modelo) throws SQLException {
        String sqlEntrada = "INSERT INTO entrada (fecha_hora_entrada, id_tarifa, id_espacio) VALUES (?, ?, ?)";
        String sqlRelacion = "INSERT INTO se_registra (placa, id_entrada) VALUES (?, ?)";

        int idTarifaDefecto = 1;
        int idEspacioDefecto = 1;

        PreparedStatement psEntrada = null;
        PreparedStatement psRelacion = null;
        ResultSet rsKey = null;

        try {
            connection.setAutoCommit(false);

            if (!existeVehiculo(ticket.getPlacaVehiculo())) {
                registrarVehiculoRapido(ticket.getPlacaVehiculo(), tipoVehiculoTexto, marca, modelo);
            }

            psEntrada = connection.prepareStatement(sqlEntrada, Statement.RETURN_GENERATED_KEYS);
            psEntrada.setTimestamp(1, Timestamp.valueOf(ticket.getFechaEntrada()));
            psEntrada.setInt(2, idTarifaDefecto);
            psEntrada.setInt(3, idEspacioDefecto);
            psEntrada.executeUpdate();

            rsKey = psEntrada.getGeneratedKeys();
            int idGenerado = 0;
            if (rsKey.next()) {
                idGenerado = rsKey.getInt(1);
                ticket.setNumeroTicket(idGenerado);
            } else {
                throw new SQLException("No se pudo obtener el ID de la entrada.");
            }

            psRelacion = connection.prepareStatement(sqlRelacion);
            psRelacion.setString(1, ticket.getPlacaVehiculo());
            psRelacion.setInt(2, idGenerado);
            psRelacion.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (connection != null) connection.setAutoCommit(true);
            cerrarRecursos(psEntrada, rsKey);
            if (psRelacion != null) psRelacion.close();
        }
    }

    public Ticket buscarTicketActivo(String placa) throws SQLException {
        String sql = "SELECT e.id_entrada, e.fecha_hora_entrada, t.precio_por_hora " +
                "FROM entrada e " +
                "JOIN se_registra sr ON e.id_entrada = sr.id_entrada " +
                "JOIN tarifa t ON e.id_tarifa = t.id_tarifa " +
                "WHERE sr.placa = ? AND e.fecha_hora_salida IS NULL";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ticket.Builder(placa)
                            .conId(rs.getInt("id_entrada"))
                            .conFechaEntrada(rs.getTimestamp("fecha_hora_entrada").toLocalDateTime())
                            .conMonto(rs.getDouble("precio_por_hora"))
                            .build();
                }
            }
        }
        return null;
    }

    public void registrarSalida(Ticket ticket) throws SQLException {
        String sql = "UPDATE entrada SET fecha_hora_salida = ?, total_pagado = ? WHERE id_entrada = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDouble(2, ticket.getMontoTotal());
            ps.setInt(3, ticket.getNumeroTicket());
            int filas = ps.executeUpdate();
            if(filas == 0) {
                throw new SQLException("No se pudo actualizar el ticket #" + ticket.getNumeroTicket());
            }
        }
    }

    public int contarVehiculosEnSitio(int idTipoVehiculo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM entrada e " +
                "JOIN se_registra sr ON e.id_entrada = sr.id_entrada " +
                "JOIN vehiculo v ON sr.placa = v.placa " +
                "WHERE e.fecha_hora_salida IS NULL AND v.id_tipo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idTipoVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    public double sumarIngresosHoy() throws SQLException {
        String sql = "SELECT SUM(total_pagado) FROM entrada WHERE DATE(fecha_hora_salida) = CURDATE()";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);
        }
        return 0.0;
    }

    private boolean existeVehiculo(String placa) throws SQLException {
        String sql = "SELECT COUNT(*) FROM vehiculo WHERE placa = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, placa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private void registrarVehiculoRapido(String placa, String tipoTexto, String marca, String modelo) throws SQLException {
        int idTipo = 1;
        if (tipoTexto != null) {
            if (tipoTexto.equalsIgnoreCase("Motocicleta")) idTipo = 2;
            if (tipoTexto.equalsIgnoreCase("Camioneta")) idTipo = 3;
        }

        String marcaFinal = (marca == null || marca.isEmpty()) ? "Sin especificar" : marca;
        String modeloFinal = (modelo == null || modelo.isEmpty()) ? "Sin especificar" : modelo;

        String sql = "INSERT INTO vehiculo (placa, id_tipo, id_propietario, marca, modelo) VALUES (?, ?, 1, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, placa);
            ps.setInt(2, idTipo);
            ps.setString(3, marcaFinal);
            ps.setString(4, modeloFinal);
            ps.executeUpdate();
        }
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

        String sql = "INSERT INTO vehiculo (placa, id_tipo, id_propietario, marca, modelo) VALUES (?, ?, ?, NULL, NULL)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, vehiculo.getPlaca());
            ps.setInt(2, idTipo);
            ps.setInt(3, idPropietario);

            return ps.executeUpdate() > 0;
        }
    }

    public java.util.List<Ticket> listarHistorial(java.time.LocalDate inicio, java.time.LocalDate fin) throws SQLException {
        java.util.List<Ticket> lista = new java.util.ArrayList<>();

        String sql = "SELECT e.id_entrada, s.placa, e.fecha_hora_entrada, e.fecha_hora_salida, e.total_pagado " +
                "FROM entrada e " +
                "JOIN se_registra s ON e.id_entrada = s.id_entrada " +
                "WHERE e.fecha_hora_salida IS NOT NULL " +
                "AND DATE(e.fecha_hora_salida) BETWEEN ? AND ? " +
                "ORDER BY e.fecha_hora_salida DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fin));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ticket t = new Ticket.Builder(rs.getString("placa"))
                            .conId(rs.getInt("id_entrada"))
                            .conFechaEntrada(rs.getTimestamp("fecha_hora_entrada").toLocalDateTime())
                            .conFechaSalida(rs.getTimestamp("fecha_hora_salida").toLocalDateTime())
                            .conMonto(rs.getDouble("total_pagado"))
                            .build();
                    lista.add(t);
                }
            }
        }
        return lista;
    }

    private int obtenerIdTipo(String descripcion) throws SQLException {
        String sql = "SELECT id_tipo FROM tipo WHERE descripcion = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descripcion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_tipo");
            }
        }
        return -1;
    }

    private int obtenerIdPropietario(String nombrePropietario) throws SQLException {
        String sql = "SELECT id_propietario FROM propietario WHERE nombre = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nombrePropietario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_propietario");
            }
        }
        return -1;
    }

    private void cerrarRecursos(Statement stmt, ResultSet rs) {
        try { if(rs!=null) rs.close(); if(stmt!=null) stmt.close(); } catch(Exception e){}
    }
}