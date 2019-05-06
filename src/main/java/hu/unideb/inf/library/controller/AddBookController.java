package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.BookingModel;
import hu.unideb.inf.library.model.pojo.Book;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class AddBookController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.bm = new BookModel();
    }

    /**
     * BookingModel osztály egy példánya.
     */
    private BookModel bm;

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
     * Map az input értékekhez.
     */
    private Map<String, TextField> inputFields = new HashMap<String, TextField>();

    /**
     * Könyv hozzáadása az adatbázishoz.
     * Csak akkor fogja hozzáadni, ha minden mezőt kitöltöttünk és a könyv terjedelme, illetve megjelenésének éve számként lett megadva.
     * @param event
     */
    @FXML
    // TODO: Log infók
    private void triggerAddBook(Event event) {
        inputFields.put("bookIsbnInput",bookIsbnInput);
        inputFields.put("bookTitleInput", bookTitleInput);
        inputFields.put("bookAuthorInput", bookAuthorInput);
        inputFields.put("bookPublisherInput", bookPublisherInput);
        inputFields.put("bookPagesInput", bookPagesInput);
        inputFields.put("bookSubjectsInput", bookSubjectsInput);
        inputFields.put("bookStorageSignInput", bookStorageSignInput);
        inputFields.put("bookReleaseDateInput", bookReleaseDateInput);

        if(bm.bookValidation(inputFields) == -1) {
            addBookErrorMsg.setStyle("-fx-text-fill: RED");
            addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Minden mező kitöltése kötelező!");
        } else if(bm.bookValidation(inputFields) == 0) {
            addBookErrorMsg.setStyle("-fx-text-fill: RED");
            addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Hibás mező érték!");
        } else {
            bm.pushNewBook(bookIsbnInput.getText(),bookTitleInput.getText(),bookAuthorInput.getText(),
                bookPublisherInput.getText(),Integer.parseInt(bookReleaseDateInput.getText()),
                Integer.parseInt(bookPagesInput.getText()),bookSubjectsInput.getText(),
                bookStorageSignInput.getText());

            addBookErrorMsg.setStyle("-fx-text-fill: GREEN");
            addBookErrorMsg.setText("Új könyv hozzáadva az adatbázishoz!");
            inputFields.forEach((k,v) -> v.clear());
        }

    }
}
