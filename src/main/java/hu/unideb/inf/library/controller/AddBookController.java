package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookingModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddBookController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bm = new BookingModel();
    }

    /**
     * BookingModel osztály egy példánya.
     */
    private BookingModel bm;

    /**
     * A könyv hozzáadása ablak ISBN szám mezőjének input értéke.
     */
    @FXML
    private TextField bookIsbnInput;

    /**
     * A könyv hozzáadása ablak könyv címe mezőjének input értéke.
     */
    @FXML
    private TextField bookTitleInput;

    /**
     * A könyv hozzáadása ablak könyv szerző mezőjének input értéke.
     */
    @FXML
    private TextField bookAuthorInput;

    /**
     * A könyv hozzáadása ablak könyv kiadója mezőjének input értéke.
     */
    @FXML
    private TextField bookPublisherInput;

    /**
     * A könyv hozzáadása ablak könyv terjedelme mezőjének input értéke.
     */
    @FXML
    private TextField bookPagesInput;

    /**
     * A könyv hozzáadása ablak könyv tárgyszavai mezőjének input értéke.
     */
    @FXML
    private TextField bookSubjectsInput;

    /**
     * A könyv hozzáadása ablak könyv raktári jelzete mezőjének input értéke.
     */
    @FXML
    private TextField bookStorageSignInput;

    /**
     * A könyv hozzáadása ablak könyv kiadásának éve mezőjének input értéke.
     */
    @FXML
    private TextField bookReleaseDateInput;

    /**
     * A könyv hozzáadása ablak error üzenetének labelje.
     */
    @FXML
    private Label addBookErrorMsg;

    /**
     * Lista az input értékekhez.
     */
    private List<TextField> inputFields;

    /**
     * Könyv hozzáadása az adatbázishoz.
     * Csak akkor fogja hozzáadni, ha minden mezőt kitöltöttünk és a könyv terjedelme, illetve megjelenésének éve számként lett megadva.
     * @param event
     */
    @FXML
    // TODO: Log infók
    private void triggerAddBook(Event event) {
        inputFields = Arrays.asList(bookIsbnInput,bookTitleInput, bookAuthorInput, bookPublisherInput, bookPagesInput,bookSubjectsInput, bookStorageSignInput, bookReleaseDateInput);

        if(inputFields.stream().anyMatch(textField -> textField.getText().trim().isEmpty())) {
            addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Minden mező kitöltése kötelező!");
        } else {
            try {
                int bookPagesInputInt = Integer.parseInt(bookPagesInput.getText());

                try {
                    int bookReleaseDateInputInt = Integer.parseInt(bookReleaseDateInput.getText());

                    bm.pushNewBook(bookIsbnInput.getText(),bookTitleInput.getText(),bookAuthorInput.getText(),bookPublisherInput.getText(),bookReleaseDateInputInt,bookPagesInputInt,bookSubjectsInput.getText(),bookStorageSignInput.getText());

                    addBookErrorMsg.setStyle("-fx-text-fill: GREEN");
                    addBookErrorMsg.setText("Új könyv hozzáadva az adatbázishoz!");
                    inputFields.forEach(textField -> textField.clear());
                } catch (NumberFormatException ex) {
                    addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Hibás megjelenés éve mező érték!");
                }
            } catch (NumberFormatException ex) {
                addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Hibás terjedelem mező érték!");
            }

        }

    }
}
