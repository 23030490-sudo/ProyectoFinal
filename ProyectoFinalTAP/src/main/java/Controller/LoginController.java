package Controller;

import DAO.UsuarioDAO;
import Model.Usuario;
import View.Login;
import View.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.SQLException;

public class LoginController {

    private Login view;
    private UsuarioDAO usuarioDAO;

    public LoginController(Login view, UsuarioDAO usuarioDAO) {
        this.view = view;
        this.usuarioDAO = usuarioDAO;
        initEvents();
    }

    private void initEvents() {
        view.getBtnLogin().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                autenticarUsuario();
            }
        });
    }

    private void autenticarUsuario() {
        String user = view.getUsuarioText();
        String pass = view.getPasswordText();

        if (user.isEmpty() || pass.isEmpty()) {
            view.setMensajeError("Por favor llene todos los campos.");
            return;
        }

        try {
            Usuario usuarioEncontrado = usuarioDAO.autenticar(user, pass);

            if (usuarioEncontrado != null) {
                System.out.println("Bienvenido: " + usuarioEncontrado.getNombre());

                MainView mainView = new MainView(usuarioEncontrado);


                new MainController(mainView);


                mainView.mostrar(view.getStage());

            } else {
                view.setMensajeError("Usuario o contraseña incorrectos.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            view.setMensajeError("Error de conexión con la Base de Datos.");
        }
    }
}