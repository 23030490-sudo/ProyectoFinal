package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView {

    private BorderPane mainLayout;
    public void mostrar(Stage stage) {
        Label lblTitulo = new Label("Bienvenido Al Centro de Estancia Vehicular “El Bache Maestro”");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        lblTitulo.setWrapText(true);

        Label lblUsuario = new Label("Usuario: Admin | 24/11/2025");
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


        Button btnInicio = crearBotonMenu(" Inicio");
        Button btnEntrada = crearBotonMenu("Registrar Entrada");
        Button btnSalida = crearBotonMenu("Salida y Cobro");
        Button btnReportes = crearBotonMenu("Reportes");
        Button btnCerrar = crearBotonMenu("Cerrar Sesión");


        btnCerrar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand; -fx-pref-width: 180; -fx-alignment: CENTER-LEFT; -fx-padding: 10;");

        menu.getChildren().addAll(lblMenu, btnInicio, btnEntrada, btnSalida, btnReportes, new Region(), btnCerrar);
        VBox.setVgrow(menu.getChildren().get(5), Priority.ALWAYS);
        VBox vistaDashboard = crearVistaDashboard();

        mainLayout = new BorderPane();
        mainLayout.setTop(header);
        mainLayout.setLeft(menu);
        mainLayout.setCenter(vistaDashboard);
        btnEntrada.setOnAction(e -> {
            mainLayout.setCenter(new RegistroEntrada().getView());
        });

        btnSalida.setOnAction(e -> {
            SalidaCobro vistaSalida = new SalidaCobro();
            mainLayout.setCenter(vistaSalida.getView());
        });


        btnInicio.setOnAction(e -> {
            mainLayout.setCenter(vistaDashboard);
        });

        btnReportes.setOnAction(e -> {
            mainLayout.setCenter(new Reportes().getView());
        });


        btnCerrar.setOnAction(e -> {
            new Login().mostrar(stage);
        });


        Scene scene = new Scene(mainLayout, 1100, 650);
        stage.setTitle("Panel Principal - El Bache Maestro");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }



    private Button crearBotonMenu(String texto) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand; -fx-alignment: CENTER-LEFT; -fx-padding: 10; -fx-border-color: #ecf0f1; -fx-border-width: 1;");
        btn.setPrefWidth(180);

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f0f3f4; -fx-text-fill: #2980b9; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5; -fx-alignment: CENTER-LEFT; -fx-padding: 10; -fx-border-color: #bdc3c7;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 14px; -fx-background-radius: 5; -fx-alignment: CENTER-LEFT; -fx-padding: 10; -fx-border-color: #ecf0f1;"));
        return btn;
    }


    private VBox crearVistaDashboard() {
        VBox contenido = new VBox(20);
        contenido.setPadding(new Insets(30));
        contenido.setStyle("-fx-background-color: #eceff1;");

        Label lblBienvenida = new Label("Panel de Control");
        lblBienvenida.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        HBox tarjetas = new HBox(20);
        tarjetas.getChildren().addAll(
                crearTarjeta("AUTOS EN SITIO", "0 / 60", "#3498db"),
                crearTarjeta("MOTOS EN SITIO", "0 / 20", "#e67e22"),
                crearTarjeta("INGRESOS HOY", "$ 0", "#27ae60")
        );

        contenido.getChildren().addAll(lblBienvenida, tarjetas);
        return contenido;
    }

    private VBox crearTarjeta(String titulo, String valor, String colorHex) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(250);
        card.setPrefHeight(120);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        Label lblTitulo = new Label(titulo);
        lblTitulo.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        Label lblValor = new Label(valor);
        lblValor.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");


        Region bar = new Region();
        bar.setPrefSize(40, 4);
        bar.setStyle("-fx-background-color: " + colorHex + ";");

        card.getChildren().addAll(lblTitulo, lblValor, bar);
        return card;
    }
}