package Controller;

import DAO.TicketDAO;
import Model.Ticket;
import View.SalidaCobro;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SalidaCobroController {

    private SalidaCobro view;
    private TicketDAO ticketDAO;
    private Ticket ticketActual; // Guardamos el ticket que estamos procesando

    public SalidaCobroController(SalidaCobro view, TicketDAO ticketDAO) {
        this.view = view;
        this.ticketDAO = ticketDAO;
        initEvents();
    }

    private void initEvents() {
        // BUSCAR
        view.getBtnBuscar().setOnAction(e -> buscarVehiculo());

        // CONFIRMAR PAGO
        view.getBtnConfirmar().setOnAction(e -> procesarPago());

        // CANCELAR
        view.getBtnCancelar().setOnAction(e -> {
            view.limpiar();
            ticketActual = null;
        });
    }

    private void buscarVehiculo() {
        String placa = view.getPlaca();
        if (placa.isEmpty()) {
            view.mostrarError("Ingrese una placa para buscar.");
            return;
        }

        try {
            // 1. Buscamos en BD
            ticketActual = ticketDAO.buscarTicketActivo(placa);

            if (ticketActual != null) {
                // 2. Calculamos tiempos y costos
                LocalDateTime entrada = ticketActual.getFechaEntrada();
                LocalDateTime salida = LocalDateTime.now();

                // Calculamos duración
                Duration duracion = Duration.between(entrada, salida);
                long horas = duracion.toHours();
                long minutosRestantes = duracion.toMinutesPart();

                // LÓGICA DE COBRO:
                // Cobramos fracción como hora completa (Ej: 1h 5min = se cobran 2 horas)
                // O mínimo 1 hora de cobro.
                double horasACobrar = horas;
                if (minutosRestantes > 0 || horas == 0) {
                    horasACobrar += 1;
                }

                // El precio por hora lo trajimos en el campo 'montoTotal' del ticket desde el DAO
                double precioPorHora = ticketActual.getMontoTotal();
                double totalPagar = horasACobrar * precioPorHora;


                ticketActual.setFechaSalida(salida);
                ticketActual.setMontoTotal(totalPagar);


                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

                view.setDatosResumen(
                        ticketActual.getNumeroTicket(),
                        entrada.format(fmt),
                        salida.format(fmt),
                        horas + " hrs " + minutosRestantes + " min",
                        String.format("$ %.2f / hr", precioPorHora),
                        String.format("$ %.2f MXN", totalPagar)
                );

                view.mostrarResumen(true);

            } else {
                view.mostrarError("No se encontró un ticket activo para la placa: " + placa);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            view.mostrarError("Error al buscar en BD: " + e.getMessage());
        }
    }

    private void procesarPago() {
        if (ticketActual == null) return;

        try {

            ticketDAO.registrarSalida(ticketActual);
            view.mostrarAlerta("Pago Exitoso", "El vehículo " + ticketActual.getPlacaVehiculo() + " ha sido liberado.\nTotal cobrado: $" + ticketActual.getMontoTotal());
            view.limpiar();
            ticketActual = null;

        } catch (SQLException e) {
            e.printStackTrace();
            view.mostrarError("Error al procesar el pago: " + e.getMessage());
        }
    }
}