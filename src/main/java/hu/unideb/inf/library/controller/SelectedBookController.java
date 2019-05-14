package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.LoanModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

public class SelectedBookController implements Initializable {

    /**
     * Logger osztály egy példánya.
     */
    private Logger logger = LoggerFactory.getLogger(SelectedBookController.class);

    /**
     * A User osztály egy példánya.
     * A bejelentkezett felhasználó adatait tárolja.
     */
    private User loggedUser;

    /**
     * Map az input értékekhez.
     */
    private Map<String, TextField> inputFields = new HashMap<String, TextField>();

    /**
     * A Book osztály egy példánya.
     * A kiválasztott könyv adatait tárolja.
     */
    private Book selectedBook;

    /**
     * A Book osztály egy példánya.
     * A módosítottó könyv adatait tárolja.
     */
    private Book savedBook;

    /**
     * A BookModel egy példánya.
     */
    private BookModel bm;

    /**
     * A LoanModel egy példánya.
     */
    private LoanModel lm;

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
     * A kiválasztott könyv státusza.
     */
    @FXML
    private Label selectedBookStatus;

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
     * A kiválaszott Book objektum és User objektum átadása a kontrollernek.
     * Az adatok betöltése.
     * TextField-ek belerakása a map-ba, majd alapértelmezetten letiltjuk rajtuk a szerkesztést.
     * Az admin funkció gombok betöltése.
     * @param book a kiválasztott könyv objektuma
     * @param user a bejelentkezett felhasználó objektuma
     */
    public void init(Book book, User user) {
        selectedBook = book;
        loggedUser = user;

        loadData();

        inputFields.put("bookIsbnInput",selectedBookIsbn);
        inputFields.put("bookTitleInput", selectedBookTitle);
        inputFields.put("bookAuthorInput", selectedBookAuthor);
        inputFields.put("bookPublisherInput", selectedBookPublisher);
        inputFields.put("bookPagesInput", selectedBookPages);
        inputFields.put("bookSubjectsInput", selectedBookSubjects);
        inputFields.put("bookStorageSignInput", selectedBookStorageSign);
        inputFields.put("bookReleaseDateInput", selectedBookReleaseDate);
        inputFields.forEach((k,v) -> v.setEditable(false));

        initAdminButtons();

        if(lm.isLoanable(selectedBook.getIsbn())) {
            selectedBookStatus.setText("Hozzáférhető");
        } else {
            selectedBookStatus.setText("Kölcsönözve");
        }

    }

    /**
     * Inicializáció.
     * A TextField-eket betöltjük a map-ba.
     * A BookModel osztály példányosítása.
     * @param url URL osztály egy példánya
     * @param resourceBundle ResourceBundle osztály egy példánya
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bm = new BookModel();
        lm = new LoanModel();
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
            selectedBookSaveBtn.setVisible(false);
        } else {
            selectedBookEditBtn.setVisible(false);
            selectedBookSaveBtn.setVisible(false);
        }
    }

    /**
     * Szerkesztés bekapcsolása, ha a bejelentkezett felhasználó admin.
     * @param event egérkattintás eseménye
     */
    @FXML
    private void selectedBookEdit(Event event) {
        if(loggedUser.getAdmin()) {
            inputFields.forEach((k,v) -> v.setEditable(true));
            selectedBookSaveBtn.setVisible(true);
            logger.info("A bejelentkezett felhasználó admin. Szerkesztés gomb láthatóvá tétele.");
        }
    }

    /**
     * A frissült adatok tárolása.
     * Ha a validálás sikeres volt és történt módosítás.
     * @param event
     */
    @FXML
    private void selectedBookSave(Event event) {
        inputFields.put("bookIsbnInput",selectedBookIsbn);
        inputFields.put("bookTitleInput", selectedBookTitle);
        inputFields.put("bookAuthorInput", selectedBookAuthor);
        inputFields.put("bookPublisherInput", selectedBookPublisher);
        inputFields.put("bookPagesInput", selectedBookPages);
        inputFields.put("bookSubjectsInput", selectedBookSubjects);
        inputFields.put("bookStorageSignInput", selectedBookStorageSign);
        inputFields.put("bookReleaseDateInput", selectedBookReleaseDate);

        if(bm.bookValidation(inputFields) == 1) {
            savedBook = new Book(
                selectedBookIsbn.getText(), selectedBookTitle.getText(),
                selectedBookAuthor.getText(), selectedBookPublisher.getText(),
                Integer.parseInt(selectedBookReleaseDate.getText()), Integer.parseInt(selectedBookPages.getText()),
                selectedBookSubjects.getText(), selectedBookStorageSign.getText());

            if(!selectedBook.bookEquals(selectedBook, savedBook)) {
                bm.updateBook(savedBook);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Library - Info");
                alert.setHeaderText(null);
                alert.setContentText("Sikeres módosítás!");
                alert.showAndWait();

                logger.info("Könyv adatainak sikeres módosítása.");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Library - Info");
                alert.setHeaderText(null);
                alert.setContentText("Nem menthető a módosítás! Egyik mező értéke sem változott!");
                alert.showAndWait();

                logger.error("Könyv adatainak sikertelen módosítása! Egyik mező értéke sem változott!");
            }
        } else if(bm.bookValidation(inputFields) == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("Nem menthető a módosítás! Hibás mező érték!");
            alert.showAndWait();

            logger.error("Könyv adatainak sikertelen módosítása! Hibás mező érték!");
        } else if(bm.bookValidation(inputFields) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("Nem menthető a módosítás! Hiányos mező érték!");
            alert.showAndWait();

            logger.error("Könyv adatainak sikertelen módosítása! Hiányos mező érték!");
        }
    }
}
