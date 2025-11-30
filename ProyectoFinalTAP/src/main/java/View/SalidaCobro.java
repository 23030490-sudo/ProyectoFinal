package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SalidaCobro {


    private VBox view;


    private Label lblTicketId, lblEntrada, lblSalida, lblTiempo, lblTarifa, lblTotal;
    private VBox tarjetaResumen;
    private Button btnConfirmar;
    private TextField txtPlaca;

    public SalidaCobro() {

        view = new VBox();

        view.setPadding(new Insets(30));
        view.setSpacing(20);
        view.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        view.setMaxWidth(600);
        view.setAlignment(Pos.TOP_LEFT);


        Label lblTitulo = new Label("PROCESAR SALIDA Y COBRO");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        Separator separador = new Separator();


        HBox buscadorBox = new HBox(15);
        buscadorBox.setAlignment(Pos.CENTER_LEFT);
        buscadorBox.setPadding(new Insets(0, 0, 10, 0));

        Label lblBuscar = new Label("Buscar Placa:");
        lblBuscar.setFont(Font.font("Segoe UI", 14));

        txtPlaca = new TextField();
        txtPlaca.setPromptText("Ej. GTO-111-A");
        txtPlaca.setStyle("-fx-font-size: 14px; -fx-padding: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

        Button btnBuscar = new Button("BUSCAR");
        btnBuscar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand; -fx-padding: 8 20; -fx-background-radius: 5;");

        buscadorBox.getChildren().addAll(lblBuscar, txtPlaca, btnBuscar);


        tarjetaResumen = new VBox(10);
        tarjetaResumen.setPadding(new Insets(20));
        tarjetaResumen.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dcdcdc; -fx-border-radius: 8; -fx-background-radius: 8;");
        tarjetaResumen.setVisible(false);

        lblTicketId = new Label("RESUMEN DEL TICKET #----");
        lblTicketId.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblTicketId.setTextFill(Color.web("#7f8c8d"));

        GridPane datosGrid = new GridPane();
        datosGrid.setHgap(20);
        datosGrid.setVgap(10);


        lblEntrada = agregarDato(datosGrid, "Entrada:", "--:--", 0);
        lblSalida = agregarDato(datosGrid, "Salida:", "--:-- (Actual)", 1);
        lblTiempo = agregarDato(datosGrid, "Tiempo Total:", "--", 2);
        lblTarifa = agregarDato(datosGrid, "Tarifa Aplicada:", "$ -- / hr", 3);

        Separator sepTicket = new Separator();
        sepTicket.setPadding(new Insets(10, 0, 10, 0));


        HBox totalBox = new HBox(10);
        totalBox.setAlignment(Pos.CENTER);
        Label lblTotalTexto = new Label("TOTAL A PAGAR:");
        lblTotalTexto.setFont(Font.font("Segoe UI", 16));

        lblTotal = new Label("$ 0.00 MXN");
        lblTotal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        lblTotal.setTextFill(Color.web("#2c3e50"));

        totalBox.getChildren().addAll(lblTotalTexto, lblTotal);

        tarjetaResumen.getChildren().addAll(lblTicketId, datosGrid, sepTicket, totalBox);


        HBox accionesBox = new HBox(20);
        accionesBox.setAlignment(Pos.CENTER);
        accionesBox.setPadding(new Insets(20, 0, 0, 0));

        btnConfirmar = new Button("CONFIRMAR PAGO Y SALIDA");
        btnConfirmar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 20; -fx-background-radius: 5; -fx-cursor: hand;");
        btnConfirmar.setDisable(true);

        Button btnCancelar = new Button("CANCELAR");
        btnCancelar.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 20; -fx-background-radius: 5; -fx-cursor: hand;");

        accionesBox.getChildren().addAll(btnConfirmar, btnCancelar);


        btnBuscar.setOnAction(e -> {
            if (!txtPlaca.getText().isEmpty()) {
                simularBusquedaExitosa();
            }
        });

        btnCancelar.setOnAction(e -> {
            limpiar();
        });

        btnConfirmar.setOnAction(e -> {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Pago Exitoso");
            alerta.setHeaderText(null);
            alerta.setContentText("El vehículo ha sido liberado correctamente.");
            alerta.showAndWait();
            limpiar();
        });


        view.getChildren().addAll(lblTitulo, separador, buscadorBox, tarjetaResumen, accionesBox);
    }


    public VBox getView() {
        return view;
    }



    private Label agregarDato(GridPane grid, String titulo, String valorInicial, int fila) {
        Label lblT = new Label(titulo);
        lblT.setStyle("-fx-font-weight: bold; -fx-text-fill: #555;");
        Label lblV = new Label(valorInicial);
        lblV.setStyle("-fx-text-fill: #333;");

        grid.add(lblT, 0, fila);
        grid.add(lblV, 1, fila);
        return lblV;
    }

    private void simularBusquedaExitosa() {
        tarjetaResumen.setVisible(true);
        btnConfirmar.setDisable(false);

//      lblTicketId.setText("RESUMEN DEL TICKET #10245");
//        lblEntrada.setText("10:00 AM");
//        lblSalida.setText("12:00 PM (Actual)");
//        lblTiempo.setText("2 Horas, 0 Minutos");
//        lblTarifa.setText("$ 15.00 / hora");
//        lblTotal.setText("$ 30.00 MXN");
    }

    private void limpiar() {
        txtPlaca.clear();
        tarjetaResumen.setVisible(false);
        btnConfirmar.setDisable(true);
    }
}