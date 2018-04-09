/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopur9futlit;

import hopur9fvinnsla.Flight;
import hopur9fvinnsla.FlightService;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML
    private TableView flightResults;
    @FXML
    private TableColumn<Flight, String> airlineColumn;
    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> originColumn;
    @FXML
    private TableColumn<Flight, String> destinationColumn;
    @FXML
    private TableColumn<Flight, Integer> adultPriceColumn;
    @FXML
    private TableColumn<Flight, Integer> childPriceColumn;
    @FXML
    private TableColumn<Flight, Integer> durationColumn;
    @FXML
    private TableColumn<Flight, Boolean> disabilityColumn;
    @FXML
    private TableColumn<Flight, Boolean> animalColumn;
    @FXML
    private TableColumn<Flight, Date> departureColumn;
    @FXML
    private TableColumn<Flight, Date> arrivalColumn;
    @FXML
    private TableColumn<Flight, Integer> handluggagePriceColumn;
    @FXML
    private TableColumn<Flight, Integer> luggagePriceColumn;

    private ObservableList<Flight> data = FXCollections.observableArrayList();
    @FXML
    private Button bookingButton;

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
        if (result.get() == confirm) {
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

    /**
     * Generates list of flights from database by using the FlightService class
     * @param origin origin of the flight.
     * @param dest destination of the flight.
     * @param date the departure date of the flight.
     * @return 
     */
    private List<Flight> getFlights(String origin, String dest, LocalDate date) {
        FlightService fs = new FlightService();
        return fs.getFlights(origin, dest, date);
    }

    /**
     * Inserts rows into the flightResutls table.
     * @param flights List of flights to insert into the flightResults table.
     */
    private void setFlightResultsTable(List<Flight> flights) {

        flights.forEach((flight) -> {
            data.add(flight);
        });
        flightResults.setItems(data);
        airlineColumn.setCellValueFactory(new PropertyValueFactory<>("airline"));
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        originColumn.setCellValueFactory(new PropertyValueFactory<>("origin"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        adultPriceColumn.setCellValueFactory(new PropertyValueFactory<>("adultPrice"));
        childPriceColumn.setCellValueFactory(new PropertyValueFactory<>("childPrice"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        disabilityColumn.setCellValueFactory(new PropertyValueFactory<>("disabilityAccess"));
        animalColumn.setCellValueFactory(new PropertyValueFactory<>("animalTransfer"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        handluggagePriceColumn.setCellValueFactory(new PropertyValueFactory<>("handLuggagePrice"));
        luggagePriceColumn.setCellValueFactory(new PropertyValueFactory<>("luggagePrice"));

    }

    @FXML
    private void searchActionPerformed(ActionEvent event) {

        String originValue = origin.getText();
        String destinationValue = destination.getText();
        int numAdultsValue = numAdults.getValue();
        int numChildrenValue = numChildren.getValue();
        LocalDate dateValue = date.getValue();

        List<String> validation = errorValidation(originValue, destinationValue, numAdultsValue, numChildrenValue, dateValue);

        if (validation.size() > 0) {
            errorValidation.setText("errors");
        } else {
            List<Flight> flights = this.getFlights(originValue, destinationValue, dateValue);
            this.setFlightResultsTable(flights);
        }

    }

    private List<String> errorValidation(String originValue, String destinationValue, int numAdultsValue, int numChildrenValue, LocalDate dateValue) {
        List<String> validation = new ArrayList();
        return validation;
    }

}
