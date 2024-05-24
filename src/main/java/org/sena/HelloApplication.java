package org.sena;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        URL url = getClass().getResource("/screen/Login.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1000, 650));
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}
