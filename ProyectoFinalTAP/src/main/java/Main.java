import View.Login;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Login login = new Login();
            login.mostrar(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Ocurrió un error al iniciar la aplicación.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}