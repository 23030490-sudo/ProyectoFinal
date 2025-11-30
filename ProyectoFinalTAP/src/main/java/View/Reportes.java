package View;

import Model.Ticket;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

public class Reportes {

    private VBox view;


    private TableView<Ticket> tablaReportes;
    private DatePicker dpInicio, dpFin;
    private Label lblTotalGanancias, lblTotalAutos;

    public Reportes() {
        view = new VBox();
        view.setPadding(new Insets(30));
        view.setSpacing(15);
        view.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        view.setMaxWidth(800);
        view.setAlignment(Pos.TOP_LEFT);


        Label lblTitulo = new Label("REPORTES Y MOVIMIENTOS");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        Separator separador = new Separator();

        HBox filtrosBox = new HBox(15);
        filtrosBox.setAlignment(Pos.CENTER_LEFT);
        filtrosBox.setPadding(new Insets(0, 0, 10, 0));

        Label lblDe = new Label("Desde:");
        dpInicio = new DatePicker();

        Label lblHasta = new Label("Hasta:");
        dpFin = new DatePicker();

        Button btnFiltrar = new Button("FILTRAR RESULTADOS");
        btnFiltrar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        filtrosBox.getChildren().addAll(lblDe, dpInicio, lblHasta, dpFin, btnFiltrar);


        tablaReportes = new TableView<>();
        tablaReportes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaReportes.setPrefHeight(300);


        TableColumn<Ticket, String> colId = new TableColumn<>("ID Ticket");
        colId.setCellValueFactory(new PropertyValueFactory<>("numeroTicket"));

        TableColumn<Ticket, String> colPlaca = new TableColumn<>("Placa");
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placaVehiculo"));

        TableColumn<Ticket, String> colEntrada = new TableColumn<>("Hora Entrada");
        colEntrada.setCellValueFactory(new PropertyValueFactory<>("fechaEntrada"));

        TableColumn<Ticket, String> colSalida = new TableColumn<>("Hora Salida");
        colSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));

        TableColumn<Ticket, Double> colTotal = new TableColumn<>("Total Pagado");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));


        colTotal.setCellFactory(tc -> new TableCell<Ticket, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$ %.2f", item));
                    setStyle("-fx-alignment: CENTER-RIGHT; -fx-font-weight: bold; -fx-text-fill: #27ae60;");
                }
            }
        });

        tablaReportes.getColumns().addAll(colId, colPlaca, colEntrada, colSalida, colTotal);


        HBox resumenBox = new HBox(30);
        resumenBox.setAlignment(Pos.CENTER_RIGHT);
        resumenBox.setPadding(new Insets(10));
        resumenBox.setStyle("-fx-background-color: #f1f2f6; -fx-background-radius: 5;");

        lblTotalAutos = new Label("Vehículos Atendidos: 0");
        lblTotalAutos.setFont(Font.font("Segoe UI", 14));

        lblTotalGanancias = new Label("Total Ingresos: $ 0.00");
        lblTotalGanancias.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblTotalGanancias.setTextFill(Color.web("#27ae60"));

        resumenBox.getChildren().addAll(lblTotalAutos, lblTotalGanancias);

        HBox exportBox = new HBox();
        exportBox.setAlignment(Pos.CENTER);

        Button btnPDF = new Button("EXPORTAR REPORTE (PDF)");
        btnPDF.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-cursor: hand;");


        exportBox.getChildren().add(btnPDF);


        btnFiltrar.setOnAction(e -> {

            System.out.println("Consultando base de datos...");
        });

        btnPDF.setOnAction(e -> {
            System.out.println("Generando PDF...");
        });


        view.getChildren().addAll(lblTitulo, separador, filtrosBox, tablaReportes, resumenBox, exportBox);
    }

    public VBox getView() {
        return view;
    }
}