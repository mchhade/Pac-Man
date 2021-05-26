/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Mahmoud
 */
public class SettingController implements Initializable {

    
    @FXML private ComboBox<String> comboBox;
    @FXML private Button upField;
    @FXML private Button downField;
    @FXML private Button rightField;
    @FXML private Button leftField;
    @FXML private CheckBox sound;
    private String buttonName;
    ObservableList<String> list=FXCollections.observableArrayList("600*700","Full Screen");
    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleOnKeyPressed(KeyEvent event){
        String up=upField.getText();
        String down=downField.getText();
        String left=leftField.getText();
        String right=rightField.getText();
        ArrayList<String> direction=new ArrayList<>();
        direction.add(up);
        direction.add(down);
        direction.add(left);
        direction.add(right);
        Button button=(Button)event.getSource();
        buttonName=button.getText();
        button.setStyle("-fx-background-color: wheat");
        if(!direction.contains(event.getCode().toString()) || buttonName.equals(event.getCode()+"")){
            button.setText("");
            button.setText(event.getCode()+"");
        }
        else{
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.setContentText("Choose another Button");
            alert.show();
        }
        
    }
    @FXML
    private void handleOnClick(ActionEvent event){
        Button button=(Button)event.getSource();
        buttonName=button.getText();
        switch(buttonName){
            case "UP":
            leftField.setStyle("-fx-background-color: wheat");
            rightField.setStyle("-fx-background-color: wheat");
            downField.setStyle("-fx-background-color: wheat");
            break;
            case "DOWN":
                leftField.setStyle("-fx-background-color: wheat");
                rightField.setStyle("-fx-background-color: wheat");
                upField.setStyle("-fx-background-color: wheat");
                break;
            case "LEFT":
                downField.setStyle("-fx-background-color: wheat");
                rightField.setStyle("-fx-background-color: wheat");
                upField.setStyle("-fx-background-color: wheat");
                    break;
            case "RIGHT":
                leftField.setStyle("-fx-background-color: wheat");
                downField.setStyle("-fx-background-color: wheat");
                upField.setStyle("-fx-background-color: wheat");
                break;
                 
        }
        button.setStyle("-fx-background-color: Red");
    }
    @FXML
    private void cancelHandel(ActionEvent event) throws IOException{
        Node source=(Node) event.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
        Parent root = loader.load();
        stage.setTitle("Pac-Man");
        root.getStylesheets().add(getClass().getResource("mainPage.css").toExternalForm());
        stage.setScene(new Scene(root,500,500));
        stage.show();
        root.requestFocus();
    }
    @FXML
    private void applyHandle(ActionEvent event) throws IOException{
        Node source=(Node) event.getSource();
        Stage stage=(Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainPage.fxml"));
        Parent root = loader.load();
        stage.setTitle("Pac-Man");
        mainPageController.flag = sound.isSelected();
        //UP
        if(!upField.getText().equals("UP")) {
            Controller.keyCodes[0]=KeyCode.getKeyCode(upField.getText());
        } else {
            Controller.keyCodes[0]=KeyCode.UP;
        }
        //DOWN
        if(!downField.getText().equals("DOWN")) {
            Controller.keyCodes[1]=KeyCode.getKeyCode(upField.getText());
        } else {
            Controller.keyCodes[1]=KeyCode.DOWN;
        }
        if(!leftField.getText().equals("LEFT")) {
            Controller.keyCodes[2]=KeyCode.getKeyCode(upField.getText());
        } else {
            Controller.keyCodes[2]=KeyCode.LEFT;
        }
        if(!rightField.getText().equals("RIGHT")) {
            Controller.keyCodes[3]=KeyCode.getKeyCode(upField.getText());
        } else {
            Controller.keyCodes[3]=KeyCode.RIGHT;
        }
        root.getStylesheets().add(getClass().getResource("mainPage.css").toExternalForm());
        stage.setScene(new Scene(root,500,500));
        stage.show();
        root.requestFocus();
    }
    @FXML
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        comboBox.setItems(list);
        upField.setText(""+Controller.keyCodes[0]);
        downField.setText(""+Controller.keyCodes[1]);
        leftField.setText(""+Controller.keyCodes[2]);
        rightField.setText(""+Controller.keyCodes[3]);
    }    
    
}
