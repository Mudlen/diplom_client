package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;


public class Main extends Application {
    private static Stage primaryStage;

    private void setPrimaryStage(Stage stage){
        Main.primaryStage = stage;
    }

    public static Stage getPrimaryStage(){
        return Main.primaryStage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Messenger");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(850);
        primaryStage.show();
        ResizeHelper.addResizeListener(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }


}
