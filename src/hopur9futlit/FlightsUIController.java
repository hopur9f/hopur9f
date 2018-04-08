/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author astalara
 */
public class FlightsUIController implements Initializable {

    @FXML
    private HBox searchContainer;
    @FXML
    private Label errorValidation;
    @FXML
    private TextField origin;
    @FXML
    private TextField destination;
    @FXML
    private Spinner<Integer> numAdults;
    @FXML
    private DatePicker date;
    @FXML
    private Button search;
    @FXML
    private Spinner<Integer> numChildren;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> numAdultsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
        numAdults.setValueFactory(numAdultsfactory);
        
        SpinnerValueFactory<Integer> numChildrenfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        numChildren.setValueFactory(numChildrenfactory);
        
        LocalDate today = LocalDate.now();
        
        
        date.setValue(LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth()));
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            
             @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
        date.setConverter(converter);
        date.setPromptText("dd-MM-yyyy");
        
    }    
    
    public void getFlights(String origin, String destination, Date departure, int numberAdults, int  numberChildren){  
    
    }

    @FXML
    private void exitActionPerformed(ActionEvent event) {
    }

}
