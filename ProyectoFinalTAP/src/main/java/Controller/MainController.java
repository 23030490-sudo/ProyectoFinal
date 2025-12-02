package Controller;

import View.Login;
import View.MainView;
import View.RegistroEntrada;
import View.SalidaCobro;
import View.Reportes;
import DAO.TicketDAO;
import DAO.UsuarioDAO;
import DAO.ConexionBD;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class MainController {

    private MainView view;
    private TicketDAO ticketDAO;

    public MainController(MainView view) {
        this.view = view;

        try {
            Connection conn = ConexionBD.getConnection();
            this.ticketDAO = new TicketDAO(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initEvents();
        actualizarDashboard();
    }

    private void initEvents() {
        view.getBtnInicio().setOnAction(e -> {
            view.mostrarDashboard();
            actualizarDashboard();
        });

        view.getBtnEntrada().setOnAction(e -> {
            try {
                RegistroEntrada vistaEntrada = new RegistroEntrada();
                new RegistroEntradaController(vistaEntrada, ticketDAO);
                view.setCentro(vistaEntrada.getView());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnSalida().setOnAction(e -> {
            try {
                SalidaCobro vistaSalida = new SalidaCobro();
                new SalidaCobroController(vistaSalida, ticketDAO);
                view.setCentro(vistaSalida.getView());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnReportes().setOnAction(e -> {
            try {
                Reportes vistaReportes = new Reportes();
                new ReportesController(vistaReportes, ticketDAO);
                view.setCentro(vistaReportes.getView());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        view.getBtnCerrar().setOnAction(e -> cerrarSesion());
    }

    private void actualizarDashboard() {
        if (ticketDAO == null) return;

        try {
            int autos = ticketDAO.contarVehiculosEnSitio(1);
            view.getLblValorAutos().setText(autos + " / 60");

            int motos = ticketDAO.contarVehiculosEnSitio(2);
            view.getLblValorMotos().setText(motos + " / 20");

            double ingresos = ticketDAO.sumarIngresosHoy();
            view.getLblValorIngresos().setText(String.format("$ %.2f", ingresos));

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void cerrarSesion() {
        try {
            Stage stage = view.getStage();
            Login loginView = new Login();

            Connection conn = ConexionBD.getConnection();
            UsuarioDAO dao = new UsuarioDAO(conn);

            new LoginController(loginView, dao);
            loginView.mostrar(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}