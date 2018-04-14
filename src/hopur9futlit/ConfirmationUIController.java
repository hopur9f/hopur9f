/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import hopur9fvinnsla.Booking;
import hopur9fvinnsla.Passenger;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Heiðdís Anna
 */
public class ConfirmationUIController implements Initializable {

    @FXML
    private VBox confirmationVBox;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Button confirm = new Button("OK");
        Label confirmationLabel = new Label("Bókun móttekin");
        confirmationVBox.getChildren().addAll(confirmationLabel, confirm);
                confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 ((Stage)confirm.getScene().getWindow()).close();
            }
             
        });
    }    
    
}
