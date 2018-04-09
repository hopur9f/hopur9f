/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

    Button confirmButton = new Button();

    @FXML
    private VBox information;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int numAdults = 2;
        information.getStyleClass().add("informationStyle");

        for (int i = 0; i < numAdults; i++) {
            VBox vBoxContainer = new VBox();
            Label grownUp = new Label("Upplýsingar um farþega:");
            grownUp.getStyleClass().add("Header");
            Label lastName = new Label("Eftirnafn:");
            TextField lastnameInput = new TextField();
            lastnameInput.setId("lastname");
            lastnameInput.setMaxWidth(450);
            Label firstName = new Label("Fornafn: ");
            TextField firstnameInput = new TextField();
            firstnameInput.setId("firstname");
            firstnameInput.setMaxWidth(450);
            Label birthdate = new Label("Fæðingardagur:");
            DatePicker datepicker = new DatePicker();
            datepicker.setId("datepicker");
            Label numBags = new Label("Töskufjöldi: ");
            Spinner numBagsInput = new Spinner();
            numBagsInput.setId("numBags");
            SpinnerValueFactory<Integer> numBagsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
            numBagsInput.setValueFactory(numBagsfactory);
            Label numHandBags = new Label("Fjöldi í handfarangri: ");
            Spinner numHandBagsInput = new Spinner();
            numHandBagsInput.setId("numHandBags");
            SpinnerValueFactory<Integer> numHandBagsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
            numHandBagsInput.setValueFactory(numHandBagsfactory);

            //útfæra sætisfjölda
            vBoxContainer.getChildren().addAll(grownUp, lastName, lastnameInput,
                    firstName, firstnameInput, birthdate, datepicker, numBags,
                    numBagsInput, numHandBags, numHandBagsInput);
            information.getChildren().add(vBoxContainer);
        }

        confirmButton.setText("Staðfesta bókun");
        information.getChildren().add(confirmButton);
        confirmButton.getStyleClass().add("confirmButton");
        confirmButton.setPrefSize(450, 20);

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                information.getChildren().forEach((vBox) -> {
                    System.out.println("************Passenger************");

                    if (vBox instanceof VBox) {
                        String lastname = "";
                        String firstname = "";
                        LocalDate birthDay = LocalDate.now();
                        String numBagsString = "";
                        String numHandBagsString = "";
                        for (Node nodeIn : ((VBox) vBox).getChildren()) {
                            String id = nodeIn.getId();
                            if (id != null) {
                                switch (id) {
                                    case "lastname":
                                        lastname = ((TextField) nodeIn).getText();
                                        break;
                                    case "firstname":
                                        firstname = ((TextField) nodeIn).getText();
                                        break;
                                    case "datepicker":
                                        birthDay = ((DatePicker) nodeIn).getValue();
                                        break;
                                    case "numBags":
                                        numBagsString = ((Spinner) nodeIn).getValue().toString();
                                        break;
                                    case "numHandBags":
                                        numHandBagsString = ((Spinner) nodeIn).getValue().toString();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        System.out.println("Lastname: " + lastname);
                        System.out.println("Firstname: " + firstname);
                        System.out.println("Birthday: " + birthDay);
                        System.out.println("Number of luggage: " + numBagsString);
                        System.out.println("Number of hand luggage: " + numHandBagsString);
                    }

                });
            }
        });
    }

}
