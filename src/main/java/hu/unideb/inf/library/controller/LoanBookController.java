package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.UserModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoanBookController implements Initializable {

    /**
     * BookModel osztály egy példánya.
     */
    private BookModel bm;

    /**
     * UserModel osztály egy példánya.
     */
    private UserModel um;

    /**
     * A book osztály elemeinek listája.
     */
    private ObservableList<Book> allLoanableBook = FXCollections.observableArrayList();

    /**
     * A User osztály elemeinek listája.
     */
    private ObservableList<User> allUser = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bm = new BookModel();
        um = new UserModel();
        loanBookTable.getColumns().removeAll();
        loanBookUserTable.getColumns().removeAll();
        initTableColumns();
        showNotAdmins();
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

    // TODO
    private void showLoanableBooks() {    }

    private void showNotAdmins() {
        allUser.addAll(um.getNotAdmins());
        loanBookUserTable.setItems(allUser);
    }
}
