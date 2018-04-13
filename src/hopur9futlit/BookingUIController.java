/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import hopur9fvinnsla.Booking;
import hopur9fvinnsla.BookingService;
import hopur9fvinnsla.Flight;
import hopur9fvinnsla.FlightService;
import hopur9fvinnsla.Passenger;
import hopur9fvinnsla.PassengerService;
import static java.lang.Math.toIntExact;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
import javafx.scene.layout.AnchorPane;
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

    Flight flight;
    int numberAdults;
    int numberChildren;
    int numberPassenger;
    int totalPrice;
    List<String> availableSeats;
    List<Passenger> passengers = new ArrayList<>();
    List<Passenger> passengersWithId = new ArrayList<>();
    List<String> seatsToRemove = new ArrayList<>();
    List<String> takenSeats = new ArrayList<>();

    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox information;
    @FXML
    private VBox errorValidationVBox = new VBox();
    @FXML
    private Label totalPriceLabel = new Label();
    @FXML
    Button confirmButton = new Button();
    
    int handLuggagePrice;
    int luggagePrice;
    
    boolean wasValidated = false; //Knows if information given has been validated
    @FXML
    private AnchorPane bookingUI;

    BookingUIController(Flight flight, int numberAdults, int numberChildren) {
        this.flight = flight;
        this.numberAdults = numberAdults;
        this.numberChildren = numberChildren;
        this.numberPassenger = numberAdults + numberChildren;
        this.totalPrice = this.flight.getAdultPrice() * numberAdults + this.flight.getChildPrice() * numberChildren;
        this.availableSeats = this.flight.getAvailableSeatList();
        this.handLuggagePrice = this.flight.getHandLuggagePrice();
        this.luggagePrice = this.flight.getLuggagePrice();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        errorValidationVBox.setId("errorValidation");
        information.getStyleClass().add("informationStyle");
        Label bookingHeader = new Label("Bókun");
        bookingHeader.getStyleClass().add("H1");

        information.getChildren().addAll(bookingHeader, errorValidationVBox);

        //Create UI input forms for each passenger.
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
            availableSeatsInput.setId("seat");
            availableSeatsInput.setValue("Veldu sæti");
            for(int j = 0; j < availableSeats.size(); j++) {
                availableSeatsInput.getItems().addAll(availableSeats.get(j));
            }
            Label handLuggagePriceLabel = new Label("Verð fyrir handfarangur: " + String.valueOf(handLuggagePrice) + " kr.");
            handLuggagePriceLabel.getStyleClass().add("element");

            Label numBags = new Label("Töskufjöldi: ");
            Spinner numBagsInput = new Spinner();
            numBagsInput.setId("numBags");
            SpinnerValueFactory<Integer> numBagsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
            numBagsInput.setValueFactory(numBagsfactory);

            Label luggagePriceLabel = new Label("Verð fyrir handfarangur: " + String.valueOf(handLuggagePrice) + " kr.");
            luggagePriceLabel.getStyleClass().add("element");           

            Label numHandBags = new Label("Fjöldi í handfarangri: ");
            Spinner numHandBagsInput = new Spinner();
            numHandBagsInput.setId("numHandBags");
            SpinnerValueFactory<Integer> numHandBagsfactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
            numHandBagsInput.setValueFactory(numHandBagsfactory);

            vBoxContainer.getChildren().addAll(grownUp, lastName, lastnameInput,
                    firstName, firstnameInput, email, emailInput, birthdate, datepicker,
                    nationality, nationalityInput, availableSeatsLabel, availableSeatsInput, luggagePriceLabel, numBags,
                    numBagsInput, handLuggagePriceLabel, numHandBags, numHandBagsInput);
            information.getChildren().add(vBoxContainer);
        }

        //UI input form for payment info
        VBox payment = new VBox();
        payment.setId("payment");
        Label paymentLabel = new Label("Greiðsluupplýsingar:");

        totalPriceLabel.setText("Flugverð: " + String.valueOf(this.totalPrice) + "kr.");
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
        expMonthInput.setValue("Mánuður");

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

        payment.getChildren().addAll(paymentLabel, totalPriceLabel, cardHolder, cardHolderInput, cardNumber, cardNumberInput, exp, expMonthInput, expYearInput, csv, csvInput);

        confirmButton.setText("Staðfesta bókun");
        confirmButton.getStyleClass().add("confirmButton");
        confirmButton.setPrefSize(450, 20);

        information.getChildren().addAll(payment, confirmButton);

        //Action handler for confirm button
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                passengers.clear();
                seatsToRemove.clear();
                takenSeats.clear();
                errorValidationVBox.getChildren().clear();

                //Loop through each element in the information VBox (passengers and payment info)
                information.getChildren().forEach((Node vBox) -> {
                    int passengerNr = 0;

                    //Get passenger info
                    if (vBox instanceof VBox && vBox.getId().contains("passenger")) {
                        passengerNr++;
                        String lastname = "";
                        String firstname = "";
                        String email = "";
                        String nationality = "";
                        LocalDate birthDay = LocalDate.now();
                        String numBagsString = "";
                        String numHandBagsString = "";
                        String seat = "";

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
                                    case "seat":
                                        seat = ((ComboBox) node).getValue().toString();
                                    default:
                                        break;
                                }
                            }
                        }

                        //Display error validation for passenger info.
                        List<String> validation = validatePassenger(firstname, lastname, email, nationality, birthDay, passengerNr, seat);
                        takenSeats.add(seat);
                        String errorString = "";
                        if (validation.size() > 0) {
                            for (String s : validation) {
                                Label errorMessage = new Label(s);
                                errorValidationVBox.getChildren().add(errorMessage);
                            }
                            wasValidated = true;
                        } else {
  //ef einn er ekki með rettar upplysingar, loggast nokkuð sá sem er með rettar upplysingar tvisvar
                            Passenger passenger = new Passenger(firstname, lastname, email, birthDay, nationality, Integer.valueOf(numHandBagsString), Integer.valueOf(numBagsString), seat);
                            passengers.add(passenger);
                            seatsToRemove.add(seat);
                            //Note we dont want to add passenger into the database here because if payment info is not on 
                            //the correct form we dont want to create booking and therefore not passenger either.
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

                        //Display error validation for passenger info.

                        int year = Integer.valueOf( expYear);
                        int month = Integer.valueOf(expMonth);
                        int day;
                        if (expMonth == "02") {
                            day = 28;
                        } else if (expMonth == "04" || expMonth == "06" || expMonth == "09" || expMonth == "10") {
                            day = 30;
                        } else {
                            day = 31;
                        }
                        LocalDate expDay = LocalDate.of(year, month, day);
                        List<String> validation = validatePaymentInfo(cardHolder, cardNumber, expDay, csv);

                        if (validation.size() > 0) {
                            for (String s : validation) {
                                Label errorMessage = new Label(s);
                                errorValidationVBox.getChildren().add(errorMessage);
                            }
                            wasValidated = true;
                        } else {
                            //Add booking into DB.
                            Booking booking = new Booking(flight, passengers, cardHolder, cardNumber, expDay, csv);
                            book(passengers, booking);
                        }
                    }

                });
                if(wasValidated) {
                        scroll.setVvalue(0.0);
                        wasValidated = false;
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Bókun móttekin");
                        alert.setHeaderText("Bókun þín hefur verið móttekin");
                        alert.setContentText("Þú hefur bókað flug. \n" 
                                + "     Fjöldi farþega: " + (numberAdults+numberChildren) + "\n"
                                + "     Brottför: " + flight.getOrigin() + " " 
                                + flight.getDeparture() + " " + flight.getDeparture().getTime() + "\n"
                                + "     Koma: " + flight.getDestination() + " " 
                                + flight.getArrival() + " " + flight.getArrival().getTime() + "\n"
                                + "Bókunarnúmer þitt er Í GLOBALBREYTU");
                        
          
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get() == ButtonType.OK) {
                            Platform.exit();
                        }
                        
                    }
                
            }
        });

    }

    /**
     * Checks whether an email is on correct form
     *
     * @param email email to validate
     * @return boolean value that describes if the email is valid or not.
     */
    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * Checks whether an card number is on correct form Only checks if it is on
     * the form nnnn-nnnn-nnnn-nnnn, where n represents number.
     *
     * @param cardNumber card number to validate
     * @return boolean value that describes if the card number is valid or not.
     */
    public boolean isValidCardNumber(String cardNumber) {
        String ePattern = "^(\\d{4})([-])(\\d{4})([-])(\\d{4})([-])(\\d{4})$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(cardNumber);
        return m.matches();
    }

    /**
     * Method that validates passenger info.
     *
     * @param firstname first name of passenger
     * @param lastname last name of passenger
     * @param email email of passenger
     * @param nationality nationality of passenger
     * @param birthday birthday of passenger
     * @param passengerNr number of passenger (each passenger in the form have
     * number so it is possible to tell the user what passenger they need to
     * fix.
     * @param seat seat number of passenger
     * @return A list with error messages.
     */
    private List<String> validatePassenger(String firstname, String lastname, String email, String nationality, LocalDate birthday, int passengerNr, String seat) {
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
        if (nationality.equals("Veldu sæti")) {
            validation.add("Vinsamlegast veljið sæti fyrir farþega númer: " + passengerNr + ".");
        }
        for (String seatTaken : takenSeats) {
            if (seatTaken.equals(seat)) {
                validation.add("Tveir farþegar geta ekki setið í sama sæti.");
            }
        }
        return validation;
    }

    /**
     * Method that validates payment info.
     *
     * @param cardHolder name of the card holder.
     * @param cardNumber card number.
     * @param csv csv number of card.
     * @return List of errormessages.
     */
    private List<String> validatePaymentInfo(String cardHolder, String cardNumber,LocalDate expDay, String csv) {
        List<String> validation = new ArrayList<>();
        if (cardHolder == null || cardHolder.isEmpty()) {
            validation.add("Vinsamlegast tilgreinið korthafa.");
        }
        if (!isValidCardNumber(cardNumber)) {
            validation.add("Kortanúmer er ekki á réttu formi.");
        }
        if (expDay.isBefore(LocalDate.now())) {
            validation.add("Kort er útrunnið");
        }
        if (csv.length() != 3) {
            validation.add("csv er ekki á réttu formi, það skal vera þriggja stafa tala.");
        }
        return validation;
    }

    /**
     * Generates array that includes id of passengers. Uses the global variable
     * passengersWithId.
     *
     * @return int[] array with id of passengers.
     */
    private int[] generatePassengersIdArray() {
        int[] passengersIntArray = new int[passengersWithId.size()];
        int counter = 0;
        for (Passenger p : passengersWithId) {
            passengersIntArray[counter] = p.getId();
            counter++;
        }
        return passengersIntArray;
    }

    private void book(List<Passenger> passengersToBook, Booking booking) {
        //add every passenger into DB and generate passenger id.
        passengersToBook.forEach(p -> {
            addPassenger(p);
        });
        addBooking(booking);
        // Bæta við staðfestingu að bókun hafi tekist! 
    }

    /**
     * Calls a function in the flightService class that inserts Passenger into
     * the DB. After passenger have been added to the DB the method adds the id
     * of that passenger into the global passengersWithId variable.
     *
     * @param passenger Passenger to include into the DB.
     */
    private void addPassenger(Passenger passenger) {
        PassengerService ps = new PassengerService();
        long id = ps.insertPassenger(passenger);
        passenger.setId(toIntExact(id));
        passengersWithId.add(passenger);
    }

    /**
     * Calls a function in the bookingService class that inserts Booking into
     * the DB. After the booking have been added the availableSeatList in the
     * flight that was booked is updated, by removing the booked seat.
     *
     * @param booking
     */
    private void addBooking(Booking booking) {
        BookingService bs = new BookingService();
        int[] passengersIntArray = generatePassengersIdArray();
        bs.insertBooking(booking, flight, passengersIntArray, numberAdults, numberChildren);
        updateAvailableSeatList();
    }

    /**
     * Calls a function in the FlightService class that updates the
     * availableSeatList for the corresponding flight. Updates the
     * availableSeatList field in the flight by removing the booked seats. Uses
     * the global removeFromSeatList variable.
     */
    private void updateAvailableSeatList() {
        FlightService fs = new FlightService();
        seatsToRemove.forEach(s -> {
            flight.removeFromSeatList(s);
        });
        fs.updateAvailableSeatList(flight, flight.getAvailableSeatList());
    }

}
