/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


/**
 *
 * @author Mahmoud
 */
public class mainPageController implements Initializable {
   public static MediaPlayer mediaPlayer;
   public static boolean flag=true;
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        Node source=(Node) event.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pacman.fxml"));
        Parent root = loader.load();
        stage.setTitle("PacMan");
        stage.setOnCloseRequest((WindowEvent event1) -> {
            System.exit(0);
        });
        Controller controller = loader.getController();
        root.setOnKeyPressed(controller);
        double sceneWidth = controller.getBoardWidth() + 20.0;
        double sceneHeight = controller.getBoardHeight() + 100.0;
        stage.setScene(new Scene(root,sceneWidth, sceneHeight));
        stage.show();
        Media media=new Media(getClass().getResource("/res/welcom.mp3").toString());
        if (flag) {
            mediaPlayer=new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
        });
        mediaPlayer.play();
        }
        root.requestFocus();
      
    }
    @FXML
    private void setting(ActionEvent event) throws IOException{
        Node source=(Node) event.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
        Parent root = loader.load();
        stage.setTitle("Setting");
        root.getStylesheets().add(getClass().getResource("setting.css").toExternalForm());
        stage.setScene(new Scene(root,500,400));
        stage.show();
        root.requestFocus();
    }
    @FXML
    private void highScore(ActionEvent event) throws IOException{
        Node source=(Node) event.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("highScore.fxml"));
        Parent root = loader.load();
        stage.setTitle("High Score");
        root.getStylesheets().add(getClass().getResource("highscore.css").toExternalForm());
        stage.setScene(new Scene(root,500,400));
        stage.show();
        root.requestFocus();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
