/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import hopur9fvinnsla.Flight;
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
    Flight flight;
    int numberAdult;
    int numberChildren;
    int numberPassenger;
    int totalPrice;

    BookingUIController(Flight flight, int numberAdult, int numberChildren) {
        this.flight = flight;
        this.numberAdult = numberAdult;
        this.numberChildren = numberChildren;
        this.numberPassenger = numberAdult + numberChildren;
        this.totalPrice = this.flight.getAdultPrice() + this.flight.getChildPrice();
    }

    @FXML
    private VBox information;

    @FXML
    private Label errorValidation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        information.getStyleClass().add("informationStyle");

        for (int i = 1; i <= numberPassenger; i++) {
            VBox vBoxContainer = new VBox();
            vBoxContainer.setId("passenger");
            Label grownUp = new Label("Upplýsingar um farþega " + i + " :");
            grownUp.getStyleClass().add("Header");
            Label lastName = new Label("Eftirnafn:");
            TextField lastnameInput = new TextField();
            lastnameInput.setId("lastname");
            lastnameInput.setMaxWidth(450);
            Label firstName = new Label("Fornafn: ");
            TextField firstnameInput = new TextField();
            firstnameInput.setId("firstname");
            firstnameInput.setMaxWidth(450);
            Label email = new Label("Netfang: ");
            TextField emailInput = new TextField();
            emailInput.setId("email");
            emailInput.setMaxWidth(450);
            Label birthdate = new Label("Fæðingardagur:");
            DatePicker datepicker = new DatePicker();
            datepicker.setId("datepicker");
            Label nationality = new Label("Þjóðerni:");
            ComboBox nationalityInput = new ComboBox();
            nationalityInput.getItems().addAll("Ísland", "Noregur", "Danmörk", "Svíþjóð", "Færeyjar", "Grænland");
            nationalityInput.setValue("Þjóðerni");
            nationalityInput.setId("nationality");
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
                    firstName, firstnameInput, email, emailInput, birthdate, datepicker, nationality, nationalityInput, numBags,
                    numBagsInput, numHandBags, numHandBagsInput);
            information.getChildren().add(vBoxContainer);
        }

        VBox payment = new VBox();
        payment.setId("payment");
        Label paymentLabel = new Label("Greiðsluupplýsingar:");
        paymentLabel.getStyleClass().add("Header");
        Label cardNumber = new Label("Kortanúmer:");
        TextField cardNumberInput = new TextField();
        cardNumberInput.setId("cardNumber");
        cardNumberInput.setMaxWidth(450);
        Label exp = new Label("Gildistími:");
        ComboBox expMonthInput = new ComboBox();
        expMonthInput.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        expMonthInput.setValue("Dagur");
        expMonthInput.setId("expMonth");
        ComboBox expYearInput = new ComboBox();
        expYearInput.getItems().addAll("2018", "2019", "2020", "2022", "2023", "2024", "2025");
        expYearInput.setValue("Ár");
        expYearInput.setId("expYear");
        Label csv = new Label("Csv:");
        TextField csvInput = new TextField();
        csvInput.setId("csv");
        csvInput.setMaxWidth(50);
        payment.getChildren().addAll(paymentLabel, cardNumber, cardNumberInput, exp, expMonthInput, expYearInput, csv, csvInput);

        confirmButton.setText("Staðfesta bókun");
        information.getChildren().addAll(payment, confirmButton);
        confirmButton.getStyleClass().add("confirmButton");
        confirmButton.setPrefSize(450, 20);

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                information.getChildren().forEach((vBox) -> {

                    //Get passenger info
                    if (vBox instanceof VBox && vBox.getId().equals("passenger")) {
                        System.out.println("************Passenger************");
                        String lastname = "";
                        String firstname = "";
                        String email = "";
                        String nationality = "";
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
                                    case "email":
                                        email = ((TextField) nodeIn).getText();
                                        break;
                                    case "nationality":
                                        nationality = ((ComboBox) nodeIn).getValue().toString();
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
                        System.out.println("Email: " + email);
                        System.out.println("Nationality: " + nationality);
                        System.out.println("Birthday: " + birthDay);
                        System.out.println("Number of luggage: " + numBagsString);
                        System.out.println("Number of hand luggage: " + numHandBagsString);
                    }
                    
                    //Get payment info
                    if (vBox instanceof VBox && vBox.getId().equals("payment")) {
                        System.out.println("************Payment************");
                        String cardNumber = "";
                        String expMonth = "";
                        String expYear = "";
                        String csv = "";
                        for (Node nodeIn : ((VBox) vBox).getChildren()) {
                            String id = nodeIn.getId();
                            if (id != null) {
                                switch (id) {
                                    case "cardNumber":
                                        cardNumber = ((TextField) nodeIn).getText();
                                        break;
                                    case "expMonth":
                                        expMonth = ((ComboBox) nodeIn).getValue().toString();
                                        break;
                                    case "expYear":
                                        expYear = ((ComboBox) nodeIn).getValue().toString();
                                        break;
                                    case "csv":
                                        csv = ((TextField) nodeIn).getText();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        System.out.println("Cardnumber: " + cardNumber);
                        System.out.println("expMonth: " + expMonth);
                        System.out.println("expYear: " + expYear);
                        System.out.println("csv: " + csv);
                    }

                });
            }
        });
    }

}
