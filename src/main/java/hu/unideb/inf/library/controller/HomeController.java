package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    /**
     * Logger osztály egy példánya.
     */
    Logger logger = LoggerFactory.getLogger(HomeController.class);

    /**
     * BookModel osztály egy példánya.
     */
    private BookModel bm;

    /**
     * A SelectedBookController egy példánya.
     */
    private SelectedBookController sbc;

    /**
     * A LoansController egy példánya.
     */
    private LoansController lc;

    /**
     * A book osztály elemeinek listája.
     */
    private ObservableList<Book> allBook = FXCollections.observableArrayList();

    /**
     * A book osztály keresett elemeinek listája.
     */
    private ObservableList<Book> searchedList = FXCollections.observableArrayList();

    /**
     * User osztály egy példánya.
     * Itt kerül eltárolásra a bejelentkezett felhasználó.
     */
    private User loggedUser;

    /**
     * A táblázatban megjelenő Book osztályok egy kiválaszott példánya.
     */
    private Book selectedBook = null;

    /**
     * Bejelentkezett felhasználó nevének labelje.
     */
    @FXML
    private Label loggedUserName;

    /**
     * Bejelentkezett felhasználó felhasználónevének labelje.
     */
    @FXML
    private Label loggedUserUsername;

    /**
     * Bejelentkezett felhasználó admin labelje.
     */
    @FXML
    private Label loggedUserAdmin;

    /**
     * Kölcsönzés gombja.
     */
    @FXML
    private Button homeScreenLoanBtn;

    /**
     * Kölcsönzés visszavételének gombja.
     */
    @FXML
    private Button homeScreenReturnedBtn;

    /**
     * Új könyv felvételének gombja.
     */
    @FXML
    private Button homeScreenAddBookBtn;

    /**
     * Kijelentkezés gombja.
     */
    @FXML
    private Button logoutBtn;

    /**
     * Kölcsönzés felvételének gombja.
     */
    @FXML
    private Button homeLoansBtn;

    /**
     * Táblázat a könyvek kilistázáshoz.
     */
    @FXML
    private TableView homeScreenTable;

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
     * A könyv címének inputja, a kereséshez.
     */
    @FXML
    private TextField searchedBookTitleInput;

    /**
     * A könyv címének szerzője, a kereséshez.
     */
    @FXML
    private TextField searchedBookAuthorInput;

    /**
     * Információk és az elérhető admin funkciók megjelenítése az ablakban
     */
    public void init() {
        setLoggedUserName();
        setLoggedUserUsername();
        setLoggedUserAdmin();
        isAdminMenu();

        if(loggedUser.getAdmin()) {
            homeLoansBtn.setDisable(true);
        }
    }

    /**
     * Inicializáció.
     * A BookModel osztály példányosítása.
     * A táblázat ürítése majd az adatbázisban lévő könyvekkel való feltöltés.
     * A kiválasztást figyelő event.
     * @param url URL osztály egy példánya
     * @param resourceBundle ResourceBundle osztály egy példánya
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bm = new BookModel();
        homeScreenTable.getColumns().removeAll();
        showAllBook();

        homeScreenTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount() == 2) {
                    triggerSelectedBookScreen();
                }
            }
        });
    }

    /**
     * User beállítása.
     * @param loggedUser bejelentkezett felhasználó objektuma
     */
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    /**
     * Táblázat oszlopainak inicializálása.
     */
    private void initTableColumns() {
        homeScreenTable.getItems().clear();
        homeScreenTable.getColumns().clear();

        titleColumn = new TableColumn("Cím");
        authorColumn = new TableColumn("Szerző");
        releaseDateColumn = new TableColumn("Megjelenés éve");

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getReleaseDate())));

        homeScreenTable.getColumns().addAll(titleColumn,authorColumn,releaseDateColumn);
        homeScreenTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Az adatbázisban található könyvek kilistázása a táblázatba.
     */
    private void showAllBook() {
        initTableColumns();

        allBook.addAll(bm.getAllBook());
        homeScreenTable.setItems(allBook);
    }

    /**
     * Keresés erdményének betöltése a táblázatba.
     */
    @FXML
    private void triggerSearch(Event event) {
        initTableColumns();

        if(!searchedBookTitleInput.getText().isBlank() || !searchedBookAuthorInput.getText().isBlank()) {
            searchedList.addAll(bm.getSearchedBooks(searchedBookTitleInput.getText(), searchedBookAuthorInput.getText()));
        }

        if(!searchedList.isEmpty()) {
            homeScreenTable.setItems(searchedList);

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Library - Info");
            alert.setHeaderText(null);
            alert.setContentText("Nincsen a keresésnek megfelelő könyv!");
            alert.showAndWait();

            searchedBookTitleInput.clear();
            searchedBookAuthorInput.clear();

            showAllBook();
        }

        logger.info("Könyvek keresése");
    }

    /**
     * Kiválasztott könyv ablakának megnyitása.
     */
    private void triggerSelectedBookScreen() {

        if(homeScreenTable.getSelectionModel().getSelectedItem() != null) {
            selectedBook = (Book) homeScreenTable.getSelectionModel().getSelectedItem();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SelectedBookScreen.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Library - Add new book");
                stage.setScene(scene);
                stage.show();

                sbc = fxmlLoader.getController();
                sbc.init(selectedBook, loggedUser);

                logger.info("Kiválasztott könyv ablaka megnyitva.");
            } catch (IOException ex) {
                logger.error("Kiválasztott könyv ablakának megnyitása sikertelen! " + ex);
            }
        }
    }

    /**
     * Új könyv hozzáadása ablak megnyitása.
     * @param event
     * @throws IOException
     */
    @FXML
    private void triggerAddBookScreen(Event event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddBookScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Library - Add new book");
            stage.setScene(scene);
            stage.show();
            logger.info("Új könyv hozzáadása ablak megnyitva.");
        } catch (IOException ex) {
            logger.error("Új könyv hozzáadása ablak megnyitása sikertelen! " + ex);
        }
    }

    /**
     * Ha a felhasználó rendelkezik admin joggal ez a metódus megjeleníti a az elérhető funkciókat.
     */
    public void isAdminMenu() {
        if(loggedUser.getAdmin()) {
            homeScreenLoanBtn.setStyle("-fx-opacity: 1");
            homeScreenReturnedBtn.setStyle("-fx-opacity: 1");
            homeScreenAddBookBtn.setStyle("-fx-opacity: 1");
        }
    }

    /**
     * Könyv kölcsönzése ablak
     * @param event
     * @throws IOException
     */
    @FXML
    private void triggerLoanScreen(Event event) throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoanBookScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Library - Loan book");
            stage.setScene(scene);
            stage.show();
            logger.info("Könyv kölcsönzése ablak megnyitva.");
        } catch (IOException ex) {
           logger.error("Könyv kölcsönzése ablak megnyitása sikertelen! " + ex);
        }
    }

    /**
     * Könyv visszavétele ablak
     * @param event
     * @throws IOException
     */
    @FXML
    private void triggerReturnScreen(Event event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ReturnBookScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Library - Return book");
            stage.setScene(scene);
            stage.show();
            logger.info("Könyv visszavételének ablaka megnyitva.");
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("Könyv visszavétele ablakának megnyitása sikertelen! " + ex);
        }
    }

    /**
     * Kölcsönzések ablakának megnyitása.
     * @param event
     */
    @FXML
    private void triggerLoansScreen(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoansScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Library - Loans");
            stage.setScene(scene);
            stage.show();

            lc = fxmlLoader.getController();
            lc.init(loggedUser);

            logger.info("Kölcsönzések ablaka megnyitva.");
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("Kölcsönzések ablakának megnyitása sikertelen! " + ex);
        }
    }

    /**
     * Kijelentkezés elsütése.
     * @param event
     */
    @FXML
    private void triggerLogout(Event event) {
        try {
            Stage stage1 = (Stage) logoutBtn.getScene().getWindow();
            stage1.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Library");
            stage.setScene(scene);

            stage.show();

            logger.info("Kijelentkezés megtörtént. Kezdőképernyő betöltve.");
        } catch (IOException ex) {
            logger.error("Hiba a kezdőképernyő betöltésénél! " + ex);
        }
    }

    /**
     * Bejelentkezett felhasználó nevének kiíratása.
     */
    public void setLoggedUserName() {
        loggedUserName.setText(loggedUser.getName());
    }

    /**
     * Bejelentkezett felhasználó felhasználónevének kiíratása.
     */
    public void setLoggedUserUsername() {
        loggedUserUsername.setText(loggedUser.getUserName());
    }

    /**
     * Bejelentkezett felhasználó admin jogának kiíratása.
     */
    public void setLoggedUserAdmin() {
        loggedUserAdmin.setText(loggedUser.getAdmin() ? "Igen" : "Nem");
    }

}
