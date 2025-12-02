package Controller;

import DAO.TicketDAO;
import Model.Ticket;
import View.RegistroEntrada;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistroEntradaController {

    private RegistroEntrada view;
    private TicketDAO ticketDAO;

    public RegistroEntradaController(RegistroEntrada view, TicketDAO ticketDAO) {
        this.view = view;
        this.ticketDAO = ticketDAO;
        initEvents();
    }

    private void initEvents() {
        view.getBtnGenerar().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                generarEntrada();
            }
        });

        view.getBtnLimpiar().setOnAction(e -> view.limpiarFormulario());
    }

    private void generarEntrada() {

        String placa = view.getPlaca();
        String marca = view.getMarca();
        String modelo = view.getModelo();
        String tipoSeleccionado = view.getTipoSeleccionado();


        if (placa == null || placa.trim().isEmpty()) {
            view.mostrarMensajeError("Error: La placa es obligatoria.");
            return;
        }

        Ticket nuevoTicket = new Ticket.Builder(placa)
                .conFechaEntrada(LocalDateTime.now())
                .build();

        try {

            ticketDAO.crearTicket(nuevoTicket, tipoSeleccionado, marca, modelo);

            view.mostrarMensajeExito("Ticket #" + nuevoTicket.getNumeroTicket() + " generado exitosamente.");
            view.limpiarFormulario();

        } catch (SQLException e) {
            e.printStackTrace();
            view.mostrarMensajeError("Error BD: " + e.getMessage());
        }
    }
}