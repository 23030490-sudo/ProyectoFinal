package View;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroEntrada {

    private VBox view;

    private Label lblHoraEntrada;
    private Label lblEstado;

    public RegistroEntrada() {

        view = new VBox();

        view.setPadding(new Insets(30));
        view.setSpacing(20);
        view.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        view.setMaxWidth(600);
        view.setAlignment(Pos.TOP_LEFT);


        Label lblTitulo = new Label("REGISTRO DE NUEVO VEHÍCULO");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web("#2c3e50"));

        Separator separador = new Separator();


        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setPadding(new Insets(20, 0, 20, 0));

        Label lblPlaca = crearLabel("Placa del Vehículo:");
        TextField txtPlaca = new TextField();
        txtPlaca.setPromptText("Ej. GTO-111-A");
        txtPlaca.setStyle("-fx-font-size: 14px; -fx-padding: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

        Label lblTipo = crearLabel("Tipo de Vehículo:");
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Automóvil", "Motocicleta", "Camioneta");
        cmbTipo.getSelectionModel().selectFirst();
        cmbTipo.setMaxWidth(Double.MAX_VALUE);
        cmbTipo.setStyle("-fx-font-size: 14px; -fx-padding: 5;");

        Label lblHoraTitulo = crearLabel("Hora de Entrada (Automática):");
        lblHoraEntrada = new Label();
        lblHoraEntrada.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        lblHoraEntrada.setTextFill(Color.web("#2980b9"));
        iniciarReloj();

        grid.add(lblPlaca, 0, 0);
        grid.add(txtPlaca, 0, 1);
        grid.add(lblTipo, 0, 2);
        grid.add(cmbTipo, 0, 3);
        grid.add(lblHoraTitulo, 0, 4);
        grid.add(lblHoraEntrada, 0, 5);


        HBox botonesBox = new HBox(15);
        botonesBox.setAlignment(Pos.CENTER_LEFT);

        Button btnGenerar = new Button("GENERAR TICKET");
        btnGenerar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;");

        Button btnLimpiar = new Button("LIMPIAR CAMPOS");
        btnLimpiar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;");

        botonesBox.getChildren().addAll(btnGenerar, btnLimpiar);


        lblEstado = new Label("> Estado: Esperando registro...");
        lblEstado.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
        lblEstado.setPadding(new Insets(10, 0, 0, 0));


        btnGenerar.setOnAction(e -> {
            if(txtPlaca.getText().isEmpty()) {
                mostrarMensajeError("Error: Debes ingresar la placa del vehículo.");
            } else {
                mostrarMensajeExito("Ticket generado para: " + txtPlaca.getText());
            }
        });

        btnLimpiar.setOnAction(e -> {
            txtPlaca.clear();
            cmbTipo.getSelectionModel().selectFirst();
            mostrarMensajeNeutro("Formulario limpio.");
        });


        view.getChildren().addAll(lblTitulo, separador, grid, botonesBox, lblEstado);
    }


    public VBox getView() {
        return view;
    }


    private Label crearLabel(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        lbl.setTextFill(Color.web("#555"));
        return lbl;
    }

    private void iniciarReloj() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss a");
            lblHoraEntrada.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void mostrarMensajeError(String msj) {
        lblEstado.setText("> " + msj);
        lblEstado.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
    }

    private void mostrarMensajeExito(String msj) {
        lblEstado.setText("> " + msj);
        lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
    }

    private void mostrarMensajeNeutro(String msj) {
        lblEstado.setText("> Estado: " + msj);
        lblEstado.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");
    }
}