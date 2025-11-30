package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.net.URL;

public class Login {

    public void mostrar(Stage stage) {
        Image imgLogo = new Image("file:src/main/resources/img/Logo.png");
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #525252;");


        ImageView logoView = null;
        if (imgLogo != null) {
            logoView = new ImageView(imgLogo);
            logoView.setFitWidth(400);
            logoView.setPreserveRatio(true);
        } else {
            System.err.println("AVISO: El logo es NULL, no se mostrará.");
        }


        Label lblTitulo = new Label("Bienvenido Al Centro de Estancia Vehicular\n\"El Bache Maestro\"");
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        lblTitulo.setMaxWidth(350);
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");
        txtUsuario.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-padding: 10;");

        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Contraseña");
        txtPass.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-padding: 10;");

        Button btnLogin = new Button("INGRESAR");
        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");

        btnLogin.setOnAction(e -> {
           if (txtUsuario.getText().equals("admin") && txtPass.getText().equals("1234")) {
                 new MainView().mostrar(stage);
            } else {
                lblError.setText("Credenciales incorrectas");
            }
        });

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(350);
        card.setPadding(new javafx.geometry.Insets(30));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        card.setEffect(new DropShadow(20, Color.rgb(0,0,0,0.3)));

        if (logoView != null) card.getChildren().add(logoView);
        card.getChildren().addAll(lblTitulo, txtUsuario, txtPass, btnLogin, lblError);

        root.getChildren().add(card);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Login - El Bache Maestro");
        stage.setScene(scene);
        stage.show();
    }

    private Image cargarImagen(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url != null) {
                System.out.println("ÉXITO: Imagen encontrada en " + url);
                return new Image(url.toExternalForm());
            } else {
                System.err.println("FALLO: No se encontró " + ruta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}