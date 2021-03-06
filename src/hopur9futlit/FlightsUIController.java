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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author astalara
 */
public class FlightsUIController implements Initializable {

    @FXML
    private VBox errorValidationVbox;
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
   /* @FXML
    private TableColumn<Flight, Integer> handluggagePriceColumn;
    @FXML
    private TableColumn<Flight, Integer> luggagePriceColumn;*/

    private ObservableList<Flight> data = FXCollections.observableArrayList();
    @FXML
    private Button bookingButton;

    final ToggleGroup group = new ToggleGroup();
    @FXML
    private RadioButton priceSort;
    @FXML
    private RadioButton timeSort;

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

        priceSort.setToggleGroup(group);
        timeSort.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                sortList(data);
            }
        });
    }

    private void sortList(List tempList) {
        if (group.getSelectedToggle() == priceSort) {
            Comparator<Flight> c = Comparator.comparingInt(Flight::getAdultPrice);
            tempList.sort(c);
        } else {
            Comparator<Flight> c = Comparator.comparingInt(Flight::getDuration);
            tempList.sort(c);
        }
    }

    @FXML
    private void exitActionPerformed(ActionEvent event) {
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
     *
     * @param origin origin of the flight.
     * @param dest destination of the flight.
     * @param date the departure date of the flight.
     * @return
     */
    private List<Flight> getFlights(String origin, String dest, LocalDate date, int numAdults, int numChildren) {
        FlightService fs = new FlightService();
        List<Flight> flights = fs.getFlights(origin, dest, date);
        sortList(flights);
        //If there are not available seats for all the guests that is searched for, the flight is removed from the result list.
        for (int i = 0; i < flights.size(); i++) {          
            if (flights.get(i).getAvailableSeatList().size() < (numAdults + numChildren)) {
                flights.remove(flights.get(i));
                i--;
            }
        }
        return flights;
    }

    /**
     * Inserts rows into the flightResutls table.
     *
     * @param flights List of flights to insert into the flightResults table.
     */
    private void setFlightResultsTable(List<Flight> flights) {
        data.clear();

        flights.forEach((flight) -> {
            data.add(flight);

        });

        if (data.isEmpty()) {
            Label noFlightsLabel = new Label("Engin flug fundust fyrir þína leit.");
            errorValidationVbox.getChildren().add(noFlightsLabel);
        } else {
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
            //handluggagePriceColumn.setCellValueFactory(new PropertyValueFactory<>("handLuggagePrice"));
           // luggagePriceColumn.setCellValueFactory(new PropertyValueFactory<>("luggagePrice"));
        }

    }

    @FXML
    private void bookingButtonActionPerformed(ActionEvent event) throws Exception {
        errorValidationVbox.getChildren().clear();
        Flight flight = (Flight) flightResults.getSelectionModel().getSelectedItem();
        int numberAdults = numAdults.getValue();
        int numberChildren = numChildren.getValue();
        if (flight == null) {
            String errorString = "Vinsamlegast veljið flug.";
            Label errorLabel = new Label(errorString);
            errorValidationVbox.getChildren().add(errorLabel);
        } else {
            BookingUIController bookingController
                    = new BookingUIController(flight, numberAdults, numberChildren);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BookingUI.fxml"));
            fxmlLoader.setController(bookingController);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        }

    }

    @FXML
    private void searchActionPerformed(ActionEvent event) {
        errorValidationVbox.getChildren().clear();
        String originValue = origin.getText();
        String destinationValue = destination.getText();
        int numAdultsValue = numAdults.getValue();
        int numChildrenValue = numChildren.getValue();
        LocalDate dateValue = date.getValue();

        List<String> validation = errorValidation(originValue, destinationValue, numAdultsValue, numChildrenValue, dateValue);

        if (validation.size() > 0) {
            validation.forEach((errorString) -> {
                Label errorLabel = new Label(errorString);
                errorValidationVbox.getChildren().add(errorLabel);
            });
        } else {
            List<Flight> flights = this.getFlights(originValue, destinationValue, dateValue, numAdultsValue, numChildrenValue);
            this.setFlightResultsTable(flights);
        }

    }

    public boolean isBeforeToday(LocalDate dateValue) {
        Date now = new Date();
        LocalDate nowDate = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (dateValue.isBefore(nowDate)) {
            return true;
        } else {
            return false;
        }
    }

    private List<String> errorValidation(String originValue, String destinationValue, int numAdultsValue, int numChildrenValue, LocalDate dateValue) {
        List<String> validation = new ArrayList();

        if ("".equals(originValue)) {
            validation.add("Vinsamlegast tilgreinið brottfararstað.");
        }
        if ("".equals(destinationValue)) {
            validation.add("Vinsamlegast tilgreinið áfangastað.");
        }
        if (numAdultsValue + numChildrenValue < 1) {
            validation.add("Vinsamlegst tilgreinið fjölda fullorðinna og barna þannig að farþegar séu fleiri en 0.");
        }
        if (dateValue == null) {
            validation.add("Vinsamlegast tilgreinið dagsetningu.");
        }
        if (isBeforeToday(dateValue)) {
            validation.add("Vinsamlegast veljið dagsetningu sem ekki er liðin.");
        }
        return validation;
    }

}
