package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.LoanModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.Loan;
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

import java.net.URL;
import java.util.ResourceBundle;

public class LoansController implements Initializable {

    /**
     * Kölcsönzött könyvek táblázata.
     */
    @FXML
    private TableView loanBookTable;

    /**
     * Táblázat egy oszlopa a kölcsönzött könyv ISBN számának.
     */
    @FXML
    private TableColumn<Loan, String> isbnColumn;

    /**
     * Táblázat egy oszlopa a kölcsönzés kezdetének.
     */
    @FXML
    private TableColumn<Loan, String> loanStartColumn;

    /**
     * Táblázat egy oszlopa a kölcsönzés végének.
     */
    @FXML
    private TableColumn<Loan, String> loanEndColumn;

    /**
     * LoanModel osztály egy példánya.
     */
    private LoanModel lm;

    /**
     * Lista a bejelentkezett felhasználó kölcsönzéseinek.
     */
    private ObservableList<Loan> userLoans = FXCollections.observableArrayList();;

    /**
     * Táblázat oszlopainak inicializálása.
     */
    private void initTableColumns() {
        loanBookTable.getItems().clear();
        loanBookTable.getColumns().clear();

        isbnColumn = new TableColumn("ISBN szám");
        loanStartColumn = new TableColumn("Kölcsönzés kezdete");
        loanEndColumn = new TableColumn("Kölcsönzés vége");

        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        isbnColumn.setCellValueFactory(new PropertyValueFactory<Loan, String>("bookISBN"));
        loanStartColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loanStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoanStart().toString()));
        loanEndColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loanEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoanEnd().toString()));

        loanBookTable.getColumns().addAll(isbnColumn, loanStartColumn, loanEndColumn);
        loanBookTable.setColumnResizePolicy(javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY);

    }

    /**
     * Kölcsönzések adatainak betöltése a táblázatba.
     */
    private void loadData() {
        loanBookTable.setItems(userLoans);
    }

    /**
     * Inicializáció.
     * Bejelentkezett User objektum átadása a controller-nek.
     * @param user bejelentkezett felhasználó User objektuma
     */
    public void init(User user) {
        userLoans.addAll(lm.userLoans(user));
    }

    /**
     * Inicializáció.
     * @param url URL osztály egy példánya
     * @param resourceBundle ResourceBundle osztály egy példánya
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lm = new LoanModel();
        initTableColumns();
        loadData();
    }
}
