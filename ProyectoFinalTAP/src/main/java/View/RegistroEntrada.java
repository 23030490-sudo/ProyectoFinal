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


    private TextField txtPlaca;
    private TextField txtMarca;
    private TextField txtModelo;
    private ComboBox<String> cmbTipo;
    private Button btnGenerar;
    private Button btnLimpiar;


    private Label lblHoraEntrada;
    private Label lblEstado;

    public RegistroEntrada() {
        view = new VBox();
        view.setPadding(new Insets(30));
        view.setSpacing(15);
        view.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");
        view.setMaxWidth(600);
        view.setAlignment(Pos.TOP_LEFT);

        Label lblTitulo = new Label("REGISTRO COMPLETO");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.web("#2c3e50"));

        Separator separador = new Separator();

        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 0, 20, 0));

        grid.add(crearLabel("Placa:"), 0, 0);
        txtPlaca = new TextField();
        txtPlaca.setPromptText("Ej. GTO-111-A");
        estilarCampo(txtPlaca);
        grid.add(txtPlaca, 0, 1);

        grid.add(crearLabel("Marca:"), 0, 2);
        txtMarca = new TextField();
        txtMarca.setPromptText("Ej. Nissan, Ford...");
        estilarCampo(txtMarca);
        grid.add(txtMarca, 0, 3);


        grid.add(crearLabel("Modelo:"), 1, 2); // Columna 1 para aprovechar espacio
        txtModelo = new TextField();
        txtModelo.setPromptText("Ej. Versa, Lobo...");
        estilarCampo(txtModelo);
        grid.add(txtModelo, 1, 3);


        grid.add(crearLabel("Tipo de Vehículo:"), 0, 4);
        cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Automóvil", "Motocicleta", "Camioneta");
        cmbTipo.getSelectionModel().selectFirst();
        cmbTipo.setMaxWidth(Double.MAX_VALUE);
        cmbTipo.setStyle("-fx-font-size: 14px; -fx-padding: 5;");
        grid.add(cmbTipo, 0, 5);


        Label lblHoraTitulo = crearLabel("Hora de Entrada:");
        lblHoraEntrada = new Label();
        lblHoraEntrada.setFont(Font.font("Monospaced", FontWeight.BOLD, 18));
        lblHoraEntrada.setTextFill(Color.web("#2980b9"));
        iniciarReloj();

        grid.add(lblHoraTitulo, 1, 4);
        grid.add(lblHoraEntrada, 1, 5);



        HBox botonesBox = new HBox(15);
        botonesBox.setAlignment(Pos.CENTER_LEFT);

        btnGenerar = new Button("GENERAR TICKET");
        btnGenerar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;");

        btnLimpiar = new Button("LIMPIAR");
        btnLimpiar.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;");

        botonesBox.getChildren().addAll(btnGenerar, btnLimpiar);

        lblEstado = new Label("> Estado: Esperando registro...");
        lblEstado.setStyle("-fx-text-fill: #7f8c8d; -fx-font-style: italic;");

        view.getChildren().addAll(lblTitulo, separador, grid, botonesBox, lblEstado);
    }

    public VBox getView() { return view; }

    private Label crearLabel(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        lbl.setTextFill(Color.web("#555"));
        return lbl;
    }

    private void estilarCampo(TextField txt) {
        txt.setStyle("-fx-font-size: 14px; -fx-padding: 8; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");
    }

    private void iniciarReloj() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss a");
            lblHoraEntrada.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void mostrarMensajeError(String msj) {
        lblEstado.setText("> Error: " + msj);
        lblEstado.setStyle("-fx-text-fill: #c0392b; -fx-font-weight: bold;");
    }

    public void mostrarMensajeExito(String msj) {
        lblEstado.setText("> Éxito: " + msj);
        lblEstado.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
    }

    public void limpiarFormulario() {
        txtPlaca.clear();
        txtMarca.clear();
        txtModelo.clear();
        cmbTipo.getSelectionModel().selectFirst();
        lblEstado.setText("> Estado: Limpio");
        lblEstado.setStyle("-fx-text-fill: #7f8c8d;");
    }


    public String getPlaca() { return txtPlaca.getText(); }
    public String getMarca() { return txtMarca.getText(); }
    public String getModelo() { return txtModelo.getText(); }
    public String getTipoSeleccionado() { return cmbTipo.getValue(); }
    public Button getBtnGenerar() { return btnGenerar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
}