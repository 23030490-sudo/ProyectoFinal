package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Login {

    // 1. INICIALIZAMOS LOS COMPONENTES AQUÍ (O EN EL CONSTRUCTOR)
    private TextField txtUsuario = new TextField();
    private PasswordField txtPass = new PasswordField();
    private Button btnLogin = new Button("INGRESAR");
    private Label lblError = new Label();

    // El stage se asigna cuando se llama a mostrar
    private Stage stage;

    public void mostrar(Stage stage) {
        this.stage = stage;

        // --- Configuración Visual ---
        Image imgLogo = null;
        try {
            imgLogo = new Image("file:src/main/resources/img/Logo.png");
        } catch (Exception e) {
            System.out.println("Logo no encontrado");
        }

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #525252;");

        ImageView logoView = null;
        if (imgLogo != null && !imgLogo.isError()) {
            logoView = new ImageView(imgLogo);
            logoView.setFitWidth(400);
            logoView.setPreserveRatio(true);
        }

        Label lblTitulo = new Label("Bienvenido Al Centro de Estancia Vehicular\n\"El Bache Maestro\"");
        lblTitulo.setWrapText(true);
        lblTitulo.setTextAlignment(TextAlignment.CENTER);
        lblTitulo.setMaxWidth(350);
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // --- Configuración de Estilos (Ya los objetos existen) ---
        txtUsuario.setPromptText("Usuario");
        txtUsuario.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-padding: 10;");

        txtPass.setPromptText("Contraseña");
        txtPass.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #ced4da; -fx-padding: 10;");

        btnLogin.setMaxWidth(Double.MAX_VALUE);
        btnLogin.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");

        lblError.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");

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

    // --- Getters ---
    public String getUsuarioText() { return txtUsuario.getText(); }
    public String getPasswordText() { return txtPass.getText(); }
    public Button getBtnLogin() { return btnLogin; }
    public void setMensajeError(String mensaje) { lblError.setText(mensaje); }
    public Stage getStage() { return stage; }
}