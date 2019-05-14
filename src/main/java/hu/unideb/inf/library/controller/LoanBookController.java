package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.LoanModel;
import hu.unideb.inf.library.model.UserModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LoanBookController implements Initializable {

    /**
     * Logger osztály egy példánya.
     */
    Logger logger = LoggerFactory.getLogger(LoanBookController.class);

    /**
     * BookModel osztály egy példánya.
     */
    private BookModel bm;

    /**
     * UserModel osztály egy példánya.
     */
    private UserModel um;

    /**
     * LoanModel osztály egy példánya.
     */
    private LoanModel lm;

    /**
     * A book osztály elemeinek listája.
     */
    private ObservableList<Book> allLoanableBook = FXCollections.observableArrayList();

    /**
     * A User osztály elemeinek listája.
     */
    private ObservableList<User> allUser = FXCollections.observableArrayList();

    /**
     * Kiválasztott könyv.
     */
    private Book selectedBook;

    /**
     * Kiválasztott felhsználó.
     */
    private User selectedUser;

    /**
     * A kikölcsönözhető könyvek táblája.
     */
    @FXML
    private TableView loanBookTable;

    /**
     * A felhasználók táblája.
     */
    @FXML
    private TableView loanBookUserTable;

    /**
     * Kiválasztott könyv ISBN számának TextField-je.
     */
    @FXML
    private TextField bookTitleField;

    /**
     * Kiválasztott könyv címének TextField-je.
     */
    @FXML
    private TextField bookIsbnField;

    /**
     * Kiválasztott felhasználó ID-jának TextField-je.
     */
    @FXML
    private TextField userIdField;

    /**
     * Kiválasztott felhasználó nevének TextField-je.
     */
    @FXML
    private TextField userNameField;

    /**
     * Táblázat egy oszlopa a könyv címének.
     */
    @FXML
    private TableColumn<Book, String> titleColumn = null;

    /**
     * Táblázat egy oszlopa a könyv szerzőjének.
     */
    @FXML
    private TableColumn<Book, String> authorColumn = null;

    /**
     * Táblázat egy oszlopa a könyv kiadásának dátumához.
     */
    @FXML
    private TableColumn<Book, String> releaseDateColumn = null;

    /**
     * Táblázat egy oszlopa a felhasználó ID-jához.
     */
    @FXML
    private TableColumn<User, Integer> userIdColumn = null;

    /**
     * Táblázat egy oszlopa a felhasználó nevéhez.
     */
    @FXML
    private TableColumn<User, String> userNameColumn = null;

    @FXML
    private Button loanBookBtn;

    /**
     * Inicializáció.
     * @param url URL osztály egy példánya
     * @param resourceBundle ResourceBundle osztály egy példánya
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bm = new BookModel();
        um = new UserModel();
        lm = new LoanModel();
        loanBookTable.getColumns().removeAll();
        loanBookUserTable.getColumns().removeAll();
        initTableColumns();
        showNotAdmins();
        showLoanableBooks();

        loanBookTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    loadSelectedBook();
                }
            }
        });

        loanBookUserTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    loadSelectedUser();
                }
            }
        });
    }

    /**
     * Táblázat oszlopainak inicializálása.
     */
    private void initTableColumns() {
        loanBookTable.getItems().clear();
        loanBookTable.getColumns().clear();

        titleColumn = new TableColumn("Cím");
        authorColumn = new TableColumn("Szerző");
        releaseDateColumn = new TableColumn("Megjelenés éve");

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getReleaseDate())));

        loanBookTable.getColumns().addAll(titleColumn,authorColumn,releaseDateColumn);
        loanBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loanBookUserTable.getItems().clear();
        loanBookUserTable.getColumns().clear();

        userIdColumn = new TableColumn("Felhasználó ID");
        userNameColumn = new TableColumn("Felhasználó neve");

        userIdColumn.setCellFactory(TextFieldTableCell.<User, Integer>forTableColumn(new IntegerStringConverter()));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        loanBookUserTable.getColumns().addAll(userIdColumn, userNameColumn);
        loanBookUserTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * A kölcsönzés felvétele az adatbázisba.
     * Ablak bezárása.
     * @param event
     */
    @FXML
    private void triggerLoanBook(Event event) {
        if(selectedBook != null && selectedUser != null) {
            lm.addLoan(selectedBook.getIsbn(), selectedUser.getId());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("A kölcsönzés sikeresen felvéve!");
            alert.showAndWait();

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            logger.info("A kölcsönzés sikeresen felvéve.");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("A kölcsönzés felvétele sikertelen! Hiányzó adatok!");
            alert.showAndWait();

            logger.error("A kölcsönzés felvétele sikertelen! Hiányzó adatok!");
        }

    }

    /**
     * Kölcsönözhető könyvek lekérése és betöltése a táblázatba.
     */
    private void showLoanableBooks() {
        allLoanableBook.addAll(bm.getLoanableBooks());
        loanBookTable.setItems(allLoanableBook);
    }

    /**
     * Admin joggal nem rendelkező felhasználók lekérése és betöltése a táblázatba.
     */
    private void showNotAdmins() {
        allUser.addAll(um.getNotAdmins());
        loanBookUserTable.setItems(allUser);
    }

    /**
     * Kiválasztott könyv adatainak betöltése a TextField-ekbe.
     */
    private void loadSelectedBook() {
        if(loanBookTable.getSelectionModel().getSelectedItem() != null) {
            selectedBook = (Book) loanBookTable.getSelectionModel().getSelectedItem();

            bookIsbnField.setText(selectedBook.getIsbn());
            bookTitleField.setText(selectedBook.getTitle());

            logger.info("Kiválasztott könyv adatainak betöltése a TextField-ekbe.");
        }
    }

    /**
     * Kiválasztott felhasználó adatainak betöltése a TextField-ekbe.
     */
    private void loadSelectedUser() {
        if(loanBookUserTable.getSelectionModel().getSelectedItem() != null) {
            selectedUser = (User) loanBookUserTable.getSelectionModel().getSelectedItem();

            userIdField.setText(String.valueOf(selectedUser.getId()));
            userNameField.setText(selectedUser.getName());

            logger.info("Kiválasztott felhasználó adatainak betöltése a TextField-ekbe.");
        }
    }
}
