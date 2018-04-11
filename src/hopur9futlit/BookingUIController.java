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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
    List<String> availableSeats;
    @FXML
    private ScrollPane scroll;

    BookingUIController(Flight flight, int numberAdult, int numberChildren) {
        this.flight = flight;
        this.numberAdult = numberAdult;
        this.numberChildren = numberChildren;
        this.numberPassenger = numberAdult + numberChildren;
        this.totalPrice = this.flight.getAdultPrice() + this.flight.getChildPrice();
        this.availableSeats = this.flight.getAvailableSeatList();
    }

    @FXML
    private VBox information;

    private VBox errorValidationVBox = new VBox();

    private Label totalPriceLabel = new Label();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String available = availableSeats.get(0);
        String[] parts = available.split(";");
       
        information.getStyleClass().add("informationStyle");
        Label bookingHeader = new Label("Bókun");
        
        bookingHeader.getStyleClass().add("H1");
        
        errorValidationVBox.setId("errorValidation");
        information.getChildren().addAll(bookingHeader, errorValidationVBox);

        for (int i = 1; i <= numberPassenger; i++) {
            VBox vBoxContainer = new VBox();
            vBoxContainer.setId("passenger" + i);
            Label grownUp = new Label("Upplýsingar um farþega " + i + " :");
            grownUp.getStyleClass().add("Header");
            Label lastName = new Label("Eftirnafn:");
            lastName.getStyleClass().add("element");
            TextField lastnameInput = new TextField();
            lastnameInput.setId("lastname");
            lastnameInput.setMaxWidth(450);
            Label firstName = new Label("Fornafn: ");
            firstName.getStyleClass().add("element");
            TextField firstnameInput = new TextField();
            firstnameInput.setId("firstname");
            firstnameInput.setMaxWidth(450);
            Label email = new Label("Netfang: ");
            email.getStyleClass().add("element");
            TextField emailInput = new TextField();
            emailInput.setId("email");
            emailInput.setMaxWidth(450);
            Label birthdate = new Label("Fæðingardagur:");
            birthdate.getStyleClass().add("element");
            DatePicker datepicker = new DatePicker();
            datepicker.setId("datepicker");
            Label nationality = new Label("Þjóðerni:");
            nationality.getStyleClass().add("element");
            ComboBox nationalityInput = new ComboBox();
            nationalityInput.getItems().addAll("Ísland", "Noregur", "Danmörk", "Svíþjóð", "Færeyjar", "Grænland");
            nationalityInput.setValue("Þjóðerni");
            nationalityInput.setId("nationality");
            Label availableSeatsLabel = new Label("Laus sæti:");
            availableSeatsLabel.getStyleClass().add("element");
            ComboBox availableSeatsInput = new ComboBox();
            availableSeatsInput.setId("availableSeats");
            availableSeatsInput.setValue("Veldu sæti");
            for(String part: parts) {
                availableSeatsInput.getItems().addAll(part);
            }
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
                    firstName, firstnameInput, email, emailInput, birthdate, datepicker,
                    nationality, nationalityInput, availableSeatsLabel, availableSeatsInput, numBags,
                    numBagsInput, numHandBags, numHandBagsInput);
            information.getChildren().add(vBoxContainer);
        }

        VBox payment = new VBox();
        payment.setId("payment");
        Label paymentLabel = new Label("Greiðsluupplýsingar:");
        totalPriceLabel.setText("Heildarverð: " + String.valueOf(this.totalPrice));
        paymentLabel.getStyleClass().add("Header");
        totalPriceLabel.getStyleClass().add("payment");
        Label cardHolder = new Label("Korthafi:");
        cardHolder.getStyleClass().add("element");
        TextField cardHolderInput = new TextField();
        cardHolderInput.setId("cardHolder");
        Label cardNumber = new Label("Kortanúmer:");
        cardNumber.getStyleClass().add("element");
        TextField cardNumberInput = new TextField();
        cardNumberInput.setId("cardNumber");
        cardNumberInput.setMaxWidth(450);
        Label exp = new Label("Gildistími:");
        exp.getStyleClass().add("element");
        ComboBox expMonthInput = new ComboBox();
        expMonthInput.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        expMonthInput.setValue("Dagur");
        expMonthInput.setId("expMonth");
        ComboBox expYearInput = new ComboBox();
        expYearInput.getItems().addAll("2018", "2019", "2020", "2022", "2023", "2024", "2025");
        expYearInput.setValue("Ár");
        expYearInput.setId("expYear");
        Label csv = new Label("Csv:");
        csv.getStyleClass().add("element");
        TextField csvInput = new TextField();
        csvInput.setId("csv");
        csvInput.setMaxWidth(50);
        payment.getChildren().addAll( paymentLabel, totalPriceLabel, cardNumber, cardNumberInput, exp, expMonthInput, expYearInput, csv, csvInput);

        confirmButton.setText("Staðfesta bókun");
        information.getChildren().addAll(payment, confirmButton);
        confirmButton.getStyleClass().add("confirmButton");
        confirmButton.setPrefSize(450, 20);

        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                errorValidationVBox.getChildren().clear();
                information.getChildren().forEach((Node vBox) -> {
                    int passengerNr = 0;
                    //Get passenger info
                    if (vBox instanceof VBox && vBox.getId().contains("passenger")) {
                        passengerNr++;
                        System.out.println("************Passenger************");
                        String lastname = "";
                        String firstname = "";
                        String email = "";
                        String nationality = "";
                        LocalDate birthDay = LocalDate.now();
                        String numBagsString = "";
                        String numHandBagsString = "";
                        String availableSeatsString = "";
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
                                    case "availableSeats":
                                        availableSeatsString = ((ComboBox) node).getValue().toString();
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
                        System.out.println("Choosen seat" + availableSeatsString);

                        //Display error validation for passenger info.
                        List<String> validation = validatePassenger(firstname, lastname, email, nationality, birthDay, passengerNr);
                        String errorString = "";
                        if (validation.size() > 0) {
                            for (String s : validation) {
                                Label errorMessage = new Label(s);
                                errorValidationVBox.getChildren().add(errorMessage);
                                scroll.setVvalue(0.0);
                            }
                        }
                    }

                    //Get payment info
                    if (vBox instanceof VBox && vBox.getId().equals("payment")) {
                        System.out.println("************Payment************");
                        String cardHolder = "";
                        String cardNumber = "";
                        String expMonth = "";
                        String expYear = "";
                        String csv = "";
                        //Loop through UI elements inside payment VBox.
                        for (Node node : ((VBox) vBox).getChildren()) {
                            String id = node.getId();
                            if (id != null) {
                                switch (id) {
                                    case "cardHolder":
                                        cardHolder = ((TextField) node).getText();
                                        break;
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
                        
                        //Display error validation for passenger info.
                        List<String> validation = validatePaymentInfo(cardHolder, cardNumber, csv);
                        String errorString = "";
                        if (validation.size() > 0) {
                            for (String s : validation) {
                                Label errorMessage = new Label(s);
                                errorValidationVBox.getChildren().add(errorMessage);
                            }
                        }
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

    public boolean isValidCardNumber(String cardNumber) {
        String ePattern = "^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(cardNumber);
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

    private List<String> validatePaymentInfo(String cardHolder, String cardNumber, String csv) {
        List<String> validation = new ArrayList<>();
        if (cardHolder == null || cardHolder.isEmpty()) {
            validation.add("Vinsamlegast tilgreinið korthafa.");
        }
        if (!isValidCardNumber(cardNumber)) {
            validation.add("Kortanúmer er ekki á réttu formi.");
        }
        if (csv.length() != 3) {
            validation.add("csv er ekki á réttu formi, það skal vera þriggja stafa tala.");
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
