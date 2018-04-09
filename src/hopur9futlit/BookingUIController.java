/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author astalara
 */
public class BookingUIController implements Initializable {
    
    @FXML
    private VBox information;

    /**
     * Initializes the controller class.
     */
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int numAdults = 5;
        information.getStyleClass().add("informationStyle");

        while(numAdults != 0) {
            Label grownUp = new Label("Upplýsingar um farþega:");
            grownUp.getStyleClass().add("Header");
            Label lastName = new Label("Eftirnafn:");
            TextField lastnameInput = new TextField();
            lastnameInput.setMaxWidth(450);
            Label firstName = new Label("Fornafn: ");
            TextField firstnameInput = new TextField();
            firstnameInput.setMaxWidth(450);
            Label birthdate = new Label("Fæðingardagur:");
            TextField dateInput = new TextField();
            dateInput.setText("Dagur");
            ComboBox monthInput = new ComboBox();
            monthInput.getItems().addAll("Janúar", "Febrúar", "Mars", "Apríl", "Maí", "Júní", 
                    "Júlí", "Ágúst", "September", "Október", "Nóvember", "Desember");
            monthInput.setValue("Mánuður");
            TextField birthYearInput = new TextField();
            birthYearInput.setText("Ár");
            HBox hbBirthDate = new HBox();
            hbBirthDate.getChildren().addAll(dateInput, monthInput, birthYearInput);
            Label numBags = new Label("Töskufjöldi: ");
            Spinner numBagsInput = new Spinner();
            SpinnerValueFactory<Integer> numBagsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
                numBagsInput.setValueFactory(numBagsfactory);
            Label numHandBags = new Label("Fjöldi í handfarangri: ");
            Spinner numHandBagsInput = new Spinner();
            SpinnerValueFactory<Integer> numHandBagsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
                numHandBagsInput.setValueFactory(numHandBagsfactory);
            
            //útfæra sætisfjölda
            information.getChildren().addAll(grownUp, lastName, lastnameInput, 
                    firstName, firstnameInput, birthdate, hbBirthDate, numBags, 
                    numBagsInput, numHandBags, numHandBagsInput);
            
            numAdults--;
        }
           
        Button confirmButton = new Button();
        confirmButton.setText("Staðfesta bókun");
        information.getChildren().add(confirmButton);
        confirmButton.getStyleClass().add("confirmButton");
        confirmButton.setPrefSize(450,20);
    }    
}
