/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import hopur9fvinnsla.Flight;
import hopur9fvinnsla.Passenger;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
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
    private Label errorValidation = new Label();

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
            information.getChildren().addAll(errorValidation, vBoxContainer);
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
                information.getChildren().forEach((Node vBox) -> {
                    int passengerNr = 0;
                    //Get passenger info
                    if (vBox instanceof VBox && vBox.getId().equals("passenger")) {
                        passengerNr++;
                        System.out.println("************Passenger************");
                        String lastname = "";
                        String firstname = "";
                        String email = "";
                        String nationality = "";
                        LocalDate birthDay = LocalDate.now();
                        String numBagsString = "";
                        String numHandBagsString = "";
                        //Loop through UI elements inside passenger VBox.
                        for (Node node : ((VBox) vBox).getChildren()) {
                            String id = node.getId();
                            if (id != null) {
                                switch (id) {
                                    case "lastname":
                                        lastname = ((TextField) node).getText();
                                        break;
                                    case "firstname":
                                        firstname = ((TextField) node).getText();
                                        break;
                                    case "email":
                                        email = ((TextField) node).getText();
                                        break;
                                    case "nationality":
                                        nationality = ((ComboBox) node).getValue().toString();
                                        break;
                                    case "datepicker":
                                        birthDay = ((DatePicker) node).getValue();
                                        break;
                                    case "numBags":
                                        numBagsString = ((Spinner) node).getValue().toString();
                                        break;
                                    case "numHandBags":
                                        numHandBagsString = ((Spinner) node).getValue().toString();
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
                        errorValidation.setText("");

                        List<String> validation = validatePassenger(firstname, lastname, email, nationality, birthDay, passengerNr);
                        System.out.println("validation" + validation);
                        String errorString = "";
                        if (validation.size() > 0) {
                            for (String s : validation) {
                                System.out.println("s: " + s);
                                System.out.println("errorString: " + errorString);
                                errorString += errorString + s + "\n";
                            }
                            errorValidation.setText(errorString);
                            errorValidation.setWrapText(true);
                            errorValidation.setMinWidth(Region.USE_PREF_SIZE);
                        }
                    }

                    //Get payment info
                    if (vBox instanceof VBox && vBox.getId().equals("payment")) {
                        System.out.println("************Payment************");
                        String cardNumber = "";
                        String expMonth = "";
                        String expYear = "";
                        String csv = "";
                        //Loop through UI elements inside payment VBox.
                        for (Node node : ((VBox) vBox).getChildren()) {
                            String id = node.getId();
                            if (id != null) {
                                switch (id) {
                                    case "cardNumber":
                                        cardNumber = ((TextField) node).getText();
                                        break;
                                    case "expMonth":
                                        expMonth = ((ComboBox) node).getValue().toString();
                                        break;
                                    case "expYear":
                                        expYear = ((ComboBox) node).getValue().toString();
                                        break;
                                    case "csv":
                                        csv = ((TextField) node).getText();
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

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private List<String> validatePassenger(String firstname, String lastname, String email, String nationality, LocalDate birthday, int passengerNr) {
        List<String> validation = new ArrayList<>();
        if (firstname == null || firstname.isEmpty()) {
            validation.add("Vinsamlegast tilgreinið fyrsta nafn farþega nr: " + passengerNr);
        }
        if (lastname == null || lastname.isEmpty()) {
            validation.add("Vinsamlegast tilgreinið föðurnafn/ættarnafn farþega nr: " + passengerNr);
        }
        if (!isValidEmailAddress(email)) {
            validation.add("Email farþega nr: " + passengerNr + " er ekki á réttu formi.");
        }
        if (nationality.equals("Þjóðerni")) {
            validation.add("Vinsamlegast veljið þjóðerni á farþega númer: " + passengerNr + ".");
        }
        if (birthday == null) {
            validation.add("Vinsamlegast veljið fæðingardag farþega númer: " + passengerNr + ".");
        }
        return validation;
    }

    private void addPassenger(Passenger passenger) {
        //todo
    }

    private void addBooking(Passenger passenger) {
        //todo
    }

}
