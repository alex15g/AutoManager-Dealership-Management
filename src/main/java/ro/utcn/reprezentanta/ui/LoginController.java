package ro.utcn.reprezentanta.ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import ro.utcn.reprezentanta.dao.UserDAO;
import ro.utcn.reprezentanta.model.User;

public class LoginController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private Label lblError;

    private final UserDAO userDAO = new UserDAO();

    @FXML

    public void handleLogin() {
        User user = userDAO.login(
                tfUsername.getText(),
                pfPassword.getText()
        );

        if (user == null) {
            lblError.setText("User sau parolă greșită!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ro/utcn/reprezentanta/ui/main.fxml")
            );

            Scene scene = new Scene(loader.load(), 1000, 600);

            MainController controller = loader.getController();
            controller.setCurrentUser(user); // 🔥 corect

            Stage stage = (Stage) tfUsername.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("AutoManager");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Eroare la încărcarea aplicației!");
        }
    }

}
