import DAO.ConexionBD;
import DAO.UsuarioDAO;
import Controller.LoginController;
import View.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            Connection connection = ConexionBD.getConnection();
            UsuarioDAO usuarioDAO = new UsuarioDAO(connection);
            Login loginView = new Login();
            new LoginController(loginView, usuarioDAO);
            loginView.mostrar(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocurrió un error al iniciar la aplicación.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}