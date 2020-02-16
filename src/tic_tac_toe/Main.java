package tic_tac_toe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private AnchorPane layout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Tic-Tac-Toe");
        primaryStage.getIcons().add(new Image("./tic_tac_toe/assets/logo.jpg"));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        initialize();
    }

    private void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("./layout/layout.fxml"));
            layout = (AnchorPane) loader.load();
            Scene scene = new Scene(layout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
