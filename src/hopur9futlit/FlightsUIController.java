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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        System.out.println("Ég fór inn í exit");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Hætta");
        alert.setContentText("Ertu viss um að þú viljir hætta");
        
        ButtonType confirm = new ButtonType("Já");
        ButtonType decline = new ButtonType("Nei");
        
        alert.getButtonTypes().setAll(confirm, decline);
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == confirm) {
            System.exit(0);
        } else {
            alert.close();
        }
        
        
    }

    @FXML
    private void aboutActionPerformed(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Um forritið");
        alert.setHeaderText("Flugleitarkerfi sem er lokaverkefni í "
                + "Þróun Hugbúnaðar við Háskóla Íslands");
        alert.setContentText(" Höfundar:\nÁsta Lára Magnúsdóttir\nHeiðdís"
                + " Anna Lúðvíksdóttir\nMargrét Valdimarsdóttir\nSigrún Dís Hauksdóttir");
        
        alert.showAndWait();
    }
   

    @FXML
    private void searchActionPerformed(ActionEvent event) {
        
        String originValue = origin.getText();
        String destinationValue = destination.getText();
        int numAdultsValue = numAdults.getValue();
        int numChildrenValue = numChildren.getValue();
        LocalDate dateValue = date.getValue();
       
        errorValidation(originValue, destinationValue, numAdultsValue, numChildrenValue, dateValue);
  
    }
    
    private List<String> errorValidation(String originValue, String destinationValue, int numAdultsValue, int numChildrenValue, LocalDate dateValue) {
        
        return null;
    }

}
