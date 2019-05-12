package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.LoanModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.Loan;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class ReturnBookController implements Initializable {

    /**
     * Kikölcsönzött könyvek táblázata.
     */
    @FXML
    private TableView loanBookTable;

    /**
     * A kiválasztott kölcsönzés ISBN száma.
     */
    @FXML
    private TextField selectedBookIsbnField;

    /**
     * A kiválasztott kölcsönzés userID-ja.
     */
    @FXML
    private TextField selectedUserIdField;

    /**
     * A táblázat egy oszlopa a könyv ISBN számának.
     */
    @FXML
    TableColumn<Loan, String> bookISBNColumn = null;

    /**
     * A táblázat egy oszlopa a felhasználó ID-jának.
     */
    @FXML
    TableColumn<Loan, Integer> userIDColumn = null;

    /**
     * A táblázate egy oszlopa a kölcsönzés kezdetének.
     */
    @FXML
    TableColumn<Loan, String> loanStartColumn = null;

    /**
     * A táblázat egy oszlopa a kölcsönzés lejártának.
     */
    @FXML
    TableColumn<Loan, String> loanEndColumn = null;

    /**
     * Lista a kölcsönzéseknek.
     */
    private ObservableList<Loan> allLoan = FXCollections.observableArrayList();

    /**
     * A LoanModel osztály egy példánya.
     */
    private LoanModel lm;

    /**
     * Kiválasztott kölcsönzés objektuma.
     */
    private Loan selectedLoan = null;

    /**
     * A kölcsönzés eltávolítása az adatábisból.
     * @param event
     */
    @FXML
    private void triggerRemoveLoan(Event event) {
        if(selectedLoan != null && selectedLoan != null) {

            lm.removeLoan(selectedLoan);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("A kölcsönzés sikeresen eltávolítva!");
            alert.showAndWait();

            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("A kölcsönzés eltávolítás sikertelen! Hiányzó adatok!");
            alert.showAndWait();
        }
    }

    /**
     * A táblázat oszlopainak inicializálása.
     */
    private void initTableColumns() {
        loanBookTable.getItems().clear();
        loanBookTable.getColumns().clear();

        bookISBNColumn = new TableColumn("Könyv ISBN száma");
        userIDColumn = new TableColumn("Felhasználói ID");
        loanStartColumn = new TableColumn("Kölcsönzés kezdete");
        loanEndColumn = new TableColumn("Kölcsönzés vége");

        bookISBNColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bookISBNColumn.setCellValueFactory(new PropertyValueFactory<Loan, String>("bookISBN"));
        userIDColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {
            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }
        }));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<Loan, Integer>("userID"));

        loanStartColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loanStartColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoanStart().toString()));
        loanEndColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        loanEndColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLoanEnd().toString()));

        loanBookTable.getColumns().addAll(bookISBNColumn, userIDColumn, loanStartColumn, loanEndColumn);
        loanBookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Kölcsönzések adatainak betöltése a táblázatba.
     */
    private void loadData() {
        allLoan.addAll(lm.allLoan());
        loanBookTable.setItems(allLoan);
    }

    /**
     * Kiválaszott kölcsönzés adatainak betöltése a TextField-ekbe.
     */
    private void loadSelectedLoan() {
        if(loanBookTable.getSelectionModel().getSelectedItem() != null) {
            selectedLoan = (Loan) loanBookTable.getSelectionModel().getSelectedItem();

            selectedBookIsbnField.setText(selectedLoan.getBookISBN());
            selectedUserIdField.setText(String.valueOf(selectedLoan.getUserID()));

            // TODO: Log infó
        }
    }

    /**
     * Inicializálás
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lm = new LoanModel();
        initTableColumns();
        loadData();

        loanBookTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    loadSelectedLoan();
                }
            }
        });
    }
}
