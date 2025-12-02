package View;

import Model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;

public class MainView {

    private BorderPane mainLayout;
    private Stage stage;

    private Button btnInicio;
    private Button btnEntrada;
    private Button btnSalida;
    private Button btnReportes;
    private Button btnCerrar;

    private Label lblValorAutos;
    private Label lblValorMotos;
    private Label lblValorIngresos;

    private VBox vistaDashboard;

    public MainView(Usuario usuarioLogueado) {

        Label lblTitulo = new Label("Bienvenido Al Centro de Estancia Vehicular “El Bache Maestro”");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        String nombre = (usuarioLogueado.getNombre() != null) ? usuarioLogueado.getNombre() : usuarioLogueado.getUsername();
        Label lblUsuario = new Label("Usuario: " + nombre + " | " + LocalDate.now());
        lblUsuario.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #2c3e50;");
        Region espacio = new Region();
        HBox.setHgrow(espacio, Priority.ALWAYS);
        header.getChildren().addAll(lblTitulo, espacio, lblUsuario);

        VBox menu = new VBox(10);
        menu.setPadding(new Insets(20));
        menu.setStyle("-fx-background-color: white; -fx-border-color: #dcdcdc; -fx-border-width: 0 1 0 0;");
        menu.setPrefWidth(220);

        Label lblMenu = new Label("MENÚ PRINCIPAL");
        lblMenu.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #95a5a6;");

        btnInicio = crearBotonMenu(" Inicio");
        btnEntrada = crearBotonMenu("Registrar Entrada");
        btnSalida = crearBotonMenu("Salida y Cobro");
        btnReportes = crearBotonMenu("Reportes");
        btnCerrar = crearBotonMenu("Cerrar Sesión");
        btnCerrar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand; -fx-pref-width: 180; -fx-alignment: CENTER-LEFT; -fx-padding: 10;");

        menu.getChildren().addAll(lblMenu, btnInicio, btnEntrada, btnSalida, btnReportes, new Region(), btnCerrar);
        VBox.setVgrow(menu.getChildren().get(5), Priority.ALWAYS);

        vistaDashboard = crearVistaDashboard();

        mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setLeft(menu);
        mainLayout.setCenter(vistaDashboard);
    }

    public void mostrar(Stage stage) {
        this.stage = stage;
        Scene scene = new Scene(mainLayout, 1100, 650);
        stage.setTitle("Panel Principal - El Bache Maestro");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    private VBox crearVistaDashboard() {
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setStyle("-fx-background-color: #eceff1;");

        Label lblBienvenida = new Label("Panel de Control");
        lblBienvenida.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        lblValorAutos = new Label("0 / 60");
        lblValorMotos = new Label("0 / 20");
        lblValorIngresos = new Label("$ 0.00");

        estilarValorTarjeta(lblValorAutos);
        estilarValorTarjeta(lblValorMotos);
        estilarValorTarjeta(lblValorIngresos);

        HBox tarjetas = new HBox(20);
        tarjetas.getChildren().addAll(
                crearTarjeta("AUTOS EN SITIO", lblValorAutos, "#3498db"),
                crearTarjeta("MOTOS EN SITIO", lblValorMotos, "#e67e22"),
                crearTarjeta("INGRESOS HOY", lblValorIngresos, "#27ae60")
        );

        contenido.getChildren().addAll(lblBienvenida, tarjetas);
        return contenido;
    }

    private void estilarValorTarjeta(Label lbl) {
        lbl.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
    }

    private VBox crearTarjeta(String titulo, Label lblValor, String colorHex) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(250);
        card.setPrefHeight(120);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");
        Region bar = new Region();
        bar.setPrefSize(40, 4);
        bar.setStyle("-fx-background-color: " + colorHex + ";");

        card.getChildren().addAll(lblTitulo, lblValor, bar);
        return card;
    }

    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand; -fx-alignment: CENTER-LEFT; -fx-padding: 10; -fx-border-color: #ecf0f1; -fx-border-width: 1;");
        btn.setPrefWidth(180);

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f0f3f4; -fx-text-fill: #2980b9; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5; -fx-alignment: CENTER-LEFT; -fx-padding: 10; -fx-border-color: #bdc3c7;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 14px; -fx-background-radius: 5; -fx-alignment: CENTER-LEFT; -fx-padding: 10; -fx-border-color: #ecf0f1;"));

        if(texto.contains("Cerrar")) {
            btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand; -fx-pref-width: 180; -fx-alignment: CENTER-LEFT; -fx-padding: 10;");
            btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand; -fx-pref-width: 180; -fx-alignment: CENTER-LEFT; -fx-padding: 10;"));
        }
        return btn;
    }

    public void setCentro(Node nodo) { mainLayout.setCenter(nodo); }
    public void mostrarDashboard() { mainLayout.setCenter(vistaDashboard); }

    public Button getBtnInicio() { return btnInicio; }
    public Button getBtnEntrada() { return btnEntrada; }
    public Button getBtnSalida() { return btnSalida; }
    public Button getBtnReportes() { return btnReportes; }
    public Button getBtnCerrar() { return btnCerrar; }
    public Stage getStage() { return stage; }

    public Label getLblValorAutos() { return lblValorAutos; }
    public Label getLblValorMotos() { return lblValorMotos; }
    public Label getLblValorIngresos() { return lblValorIngresos; }
}