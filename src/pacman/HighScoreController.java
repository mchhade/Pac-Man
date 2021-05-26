package pacman;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class HighScoreController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML private TableView<Score> scoreTable;
    @FXML private TableColumn<Score,Integer> levelNo;
    @FXML private TableColumn<Score,String> date;
    @FXML private TableColumn<Score,Integer> score;
    ObservableList<Score> scores =FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        levelNo.setCellValueFactory(new PropertyValueFactory<>("levelNo"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));
        
        scoreTable.setItems(scores);
       
            try (Scanner scanner = new Scanner(new File("src/res/score.txt"))) {
                while (scanner.hasNext()){
                    int levelNumber=scanner.nextInt();
                    String dateScore=scanner.next();
                    int scoreget=scanner.nextInt();
                    scores.add(new Score(levelNumber, dateScore, scoreget));
                }
            } catch (FileNotFoundException ex) {
            Logger.getLogger(HighScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void cancelHandel(ActionEvent event) throws IOException{
        Node source=(Node) event.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
        Parent root = loader.load();
        stage.setTitle("setting");
        root.getStylesheets().add(getClass().getResource("mainPage.css").toExternalForm());
        stage.setScene(new Scene(root,500,500));
        stage.show();
        root.requestFocus();
    }
    
}
