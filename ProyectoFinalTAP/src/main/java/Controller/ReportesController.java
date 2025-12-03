package Controller;

import DAO.TicketDAO;
import Model.Ticket;
import View.Reportes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ReportesController {

    private Reportes view;
    private TicketDAO ticketDAO;

    public ReportesController(Reportes view, TicketDAO ticketDAO) {
        this.view = view;
        this.ticketDAO = ticketDAO;
        initEvents();
        cargarHistorialHoy();
    }

    private void initEvents() {
        // Evento del botón filtrar
        view.getBtnFiltrar().setOnAction(e -> buscarPorRango());

        // Evento del botón exportar PDF
        view.getBtnPDF().setOnAction(e -> exportarPDF());
    }

    private void buscarPorRango() {
        try {
            LocalDate inicio = view.getDpInicio().getValue();
            LocalDate fin = view.getDpFin().getValue();

            // Validaciones
            if (inicio == null || fin == null) {
                mostrarAlerta("Error", "Debe seleccionar ambas fechas (Desde y Hasta)");
                return;
            }

            if (inicio.isAfter(fin)) {
                mostrarAlerta("Error", "La fecha de inicio no puede ser posterior a la fecha fin");
                return;
            }

            // Consultar historial
            List<Ticket> historial = ticketDAO.listarHistorial(inicio, fin);
            actualizarTabla(historial);

            // Calcular totales
            double totalIngresos = 0.0;
            for (Ticket ticket : historial) {
                totalIngresos += ticket.getMontoTotal();
            }

            // Actualizar labels de resumen
            view.getLblTotalAutos().setText("Vehículos Atendidos: " + historial.size());
            view.getLblTotalGanancias().setText(String.format("Total Ingresos: $ %.2f", totalIngresos));

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error de Base de Datos", "No se pudo cargar el historial: " + e.getMessage());
        }
    }

    private void cargarHistorialHoy() {
        try {
            LocalDate hoy = LocalDate.now();
            List<Ticket> historial = ticketDAO.listarHistorial(hoy, hoy);
            actualizarTabla(historial);

            // Calcular totales del día
            double totalIngresos = 0.0;
            for (Ticket ticket : historial) {
                totalIngresos += ticket.getMontoTotal();
            }

            view.getLblTotalAutos().setText("Vehículos Atendidos: " + historial.size());
            view.getLblTotalGanancias().setText(String.format("Total Ingresos: $ %.2f", totalIngresos));

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar el historial de hoy: " + e.getMessage());
        }
    }

    private void actualizarTabla(List<Ticket> historial) {
        ObservableList<Ticket> data = FXCollections.observableArrayList(historial);
        view.getTablaReportes().setItems(data);
    }

    private void exportarPDF() {
        // TODO: Implementar exportación a PDF
        mostrarAlerta("Función en desarrollo", "La exportación a PDF estará disponible próximamente.");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}