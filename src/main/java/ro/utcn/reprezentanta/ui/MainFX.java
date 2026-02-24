package ro.utcn.reprezentanta.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ro/utcn/reprezentanta/ui/login.fxml")
        );

        Scene scene = new Scene(loader.load(), 400, 300);

        stage.setTitle("AutoManager - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
