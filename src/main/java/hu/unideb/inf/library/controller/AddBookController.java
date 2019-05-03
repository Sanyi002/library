package hu.unideb.inf.library.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddBookController {
    @FXML
    private TextField bookIsbnInput;

    @FXML
    private TextField bookTitleInput;

    @FXML
    private TextField bookAuthorInput;

    @FXML
    private TextField bookPublisherInput;

    @FXML
    private TextField bookPagesInput;

    @FXML
    private TextField bookSubjectsInput;

    @FXML
    private TextField bookStorageSignInput;

    @FXML
    private TextField bookReleaseDateInput;

    @FXML
    private Label addBookErrorMsg;

    @FXML
    private void triggerAddBook(Event event) {
        if(bookIsbnInput.getText().trim().isEmpty() || bookTitleInput.getText().trim().isEmpty() ||
                bookAuthorInput.getText().trim().isEmpty() || bookPublisherInput.getText().trim().isEmpty() ||
                bookPagesInput.getText().trim().isEmpty() || bookSubjectsInput.getText().trim().isEmpty() ||
                bookStorageSignInput.getText().trim().isEmpty() || bookReleaseDateInput.getText().trim().isEmpty()) {
            addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Minden mező kitöltése kötelező!");
        } else {
            try {
                Integer.parseInt(bookPagesInput.getText());

                try {
                    Integer.parseInt(bookReleaseDateInput.getText());
                    addBookErrorMsg.setStyle("-fx-text-fill: GREEN");
                    addBookErrorMsg.setText("Új könyv hozzáadva az adatbázishoz!");
                } catch (NumberFormatException ex) {
                    addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Hibás megjelenés éve mező érték!");
                }
            } catch (NumberFormatException ex) {
                addBookErrorMsg.setText("Új könyv hozzáadása sikertelen! Hibás terjedelem mező érték!");
            }

        }

    }
}
