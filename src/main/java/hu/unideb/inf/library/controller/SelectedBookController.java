package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class SelectedBookController implements Initializable {

    /**
     * A User osztály egy példánya.
     * A bejelentkezett felhasználó adatait tárolja.
     */
    private User loggedUser;

    /**
     * TextField-ek listája, a kiválaszott könyv adatait vagy szerekeztés esetén a bemeneti adatokat tárolják.
     */
    private List<TextField> inputFields;

    /**
     * A Book osztály egy példánya.
     * A kiválasztott könyv adatait tárolja.
     */
    private Book selectedBook;

    /**
     * A kiválasztott könyv ISBN száma.
     */
    @FXML
    private TextField selectedBookIsbn;

    /**
     * A kiválasztott könyv címe.
     */
    @FXML
    private TextField selectedBookTitle;

    /**
     * A kiválasztott könyv szerzője.
     */
    @FXML
    private TextField selectedBookAuthor;

    /**
     * A kiválasztott könyv kiadója.
     */
    @FXML
    private TextField selectedBookPublisher;

    /**
     * A kiválasztott könyv terjedelme.
     */
    @FXML
    private TextField selectedBookPages;

    /**
     * A kiválasztott könyv tárgyszavai.
     */
    @FXML
    private TextField selectedBookSubjects;

    /**
     * A kiválasztott könyv megjelenésének éve.
     */
    @FXML
    private TextField selectedBookReleaseDate;

    /**
     * A kiválasztott könyv raktári jelzete.
     */
    @FXML
    private TextField selectedBookStorageSign;

    /**
     * A szerkesztés gombja.
     */
    @FXML
    private Button selectedBookEditBtn;

    /**
     * A szerkesztés mentésének gombja.
     */
    @FXML
    private Button selectedBookSaveBtn;

    /**
     * A kölcsönzés felvételének gombja.
     */
    @FXML
    private Button selectedBookLoanBtn;

    /**
     * A kiválaszott Book objektum és User objektum átadása a kontrollernek.
     * Az adatok betöltése.
     * A betöltés után alapértelmezetten letiltjuk a szerkesztést.
     * Az admin funkció gombok betöltése.
     * @param book a kiválasztott Book osztály egy példánya
     */
    public void init(Book book, User user) {
        selectedBook = book;
        loggedUser = user;

        loadData();

        inputFields.forEach(textField -> textField.setEditable(false));

        initAdminButtons();
    }

    /**
     * Inicializáció.
     * A TextField-eket betöltjük a listába.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputFields = Arrays.asList(selectedBookIsbn, selectedBookTitle, selectedBookAuthor, selectedBookPublisher,
                selectedBookPages, selectedBookSubjects, selectedBookReleaseDate, selectedBookStorageSign);
    }

    /**
     * A kiválasztott könyv adatainak betöltése a TextField-ekbe.
     */
    private void loadData() {
        selectedBookIsbn.setText(selectedBook.getIsbn());
        selectedBookTitle.setText(selectedBook.getTitle());
        selectedBookAuthor.setText(selectedBook.getAuthor());
        selectedBookPublisher.setText(selectedBook.getPublisher());
        selectedBookPages.setText(String.valueOf(selectedBook.getPages()));
        selectedBookSubjects.setText(selectedBook.getSubjects());
        selectedBookReleaseDate.setText(String.valueOf(selectedBook.getReleaseDate()));
        selectedBookStorageSign.setText(selectedBook.getStorageSign());
    }

    /**
     * Az admin funkciók eléréséhez használt gombok inicializálása.
     */
    private void initAdminButtons() {
        if(loggedUser.getAdmin()) {
            selectedBookEditBtn.setVisible(true);
            selectedBookLoanBtn.setVisible(true);
            selectedBookSaveBtn.setVisible(false);
        } else {
            selectedBookEditBtn.setVisible(false);
            selectedBookSaveBtn.setVisible(false);
            selectedBookLoanBtn.setVisible(false);
        }
    }

    /**
     * Szerkesztés bekapcsolása, ha a bejelentkezett felhasználó admin.
     * @param event egérkattintás eseménye
     */
    @FXML
    private void selectedBookEdit(Event event){
        if(loggedUser.getAdmin()) {
            inputFields.forEach(textField -> textField.setEditable(true));
            selectedBookSaveBtn.setVisible(true);
            // TODO: Log infó
        }

    }
}
