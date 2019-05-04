package hu.unideb.inf.library.controller;

import com.sun.javafx.scene.control.IntegerField;
import hu.unideb.inf.library.model.BookModel;
import hu.unideb.inf.library.model.pojo.Book;
import hu.unideb.inf.library.model.pojo.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    /**
     * Információk és az elérhető admin funkciók megjelenítése az ablakban
     */
    public void init() {
        setLoggedUserName();
        setLoggedUserUsername();
        setLoggedUserAdmin();
        isAdminMenu();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bm = new BookModel();
        homeScreenTable.getColumns().removeAll();
        showAllBook();

    }

    /**
     * BookModel osztály egy példánya.
     */
    private BookModel bm;

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
    private User user;

    /**
     * User beállítása.
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

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
     * Kölcsönzés buttonja.
     */
    @FXML
    private Button homeScreenLoanBtn;

    /**
     * Kölcsönzés visszavételének buttonja.
     */
    @FXML
    private Button homeScreenReturnedBtn;

    /**
     * Új könyv felvételének buttonja.
     */
    @FXML
    private Button homeScreenAddBookBtn;

    @FXML
    private TableView homeScreenTable;

    @FXML
    private TableColumn<Book, String> titleColumn = null;

    @FXML
    private TableColumn<Book, String> authorColumn = null;

    @FXML
    private TableColumn<Book, String> releaseDateColumn = null;

    @FXML
    private TextField searchedBookTitleInput;

    @FXML
    private TextField searchedBookAuthorInput;

    /**
     * Adatbázis oszlopainak inicializálása.
     */
    private void initTableColumns() {
        homeScreenTable.getItems().clear();
        homeScreenTable.getColumns().clear();

        titleColumn = new TableColumn("Cím");
        titleColumn.setMinWidth(300);
        authorColumn = new TableColumn("Szerző");
        authorColumn.setMinWidth(300);
        releaseDateColumn = new TableColumn("Megjelenés éve");
        releaseDateColumn.setMinWidth(300);

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        titleColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        authorColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        releaseDateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        releaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getReleaseDate())));

        homeScreenTable.getColumns().addAll(titleColumn,authorColumn,releaseDateColumn);
    }

    /**
     * Az adatbázisban található könyvek kilistázása a táblázatba.
     */
    private void showAllBook() {
        initTableColumns();

        allBook.addAll(bm.getAllBook());
        homeScreenTable.setItems(allBook);
    }

    @FXML
    private void updateHomeTable(Event event) {
        // TODO
    }

    /**
     * Keresés erdményének betöltése a táblázatba.
     */
    @FXML
    private void triggerSearch(Event event) {
        initTableColumns();

        searchedList.addAll(bm.getSearchedBooks(searchedBookTitleInput.getText(), searchedBookAuthorInput.getText()));
        System.out.println(searchedList);

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

    }

    /**
     * Új könyv hozzáadása ablak megnyitása.
     * @param event
     * @throws IOException
     */
    @FXML
    private void triggerAddBookScreen(Event event) throws IOException {
        try {
            // TODO: Log info: Új könyv hozzáadása ablak megnyitva.
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddBookScreen.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setTitle("Library - Add new book");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: Log info: Új könyv hozzáadása ablak megnyitása sikertelen.
        }
    }

    /**
     * Ha a felhasználó rendelkezik admin joggal ez a metódus megjeleníti a az elérhető funkciókat.
     */
    public void isAdminMenu() {
        if(user.getAdmin()) {
            homeScreenLoanBtn.setStyle("-fx-opacity: 1");
            homeScreenReturnedBtn.setStyle("-fx-opacity: 1");
            homeScreenAddBookBtn.setStyle("-fx-opacity: 1");
        }
    }

    /**
     * Bejelentkezett felhasználó nevének kiíratása.
     */
    public void setLoggedUserName() {
        loggedUserName.setText(user.getName());
    }

    /**
     * Bejelentkezett felhasználó felhasználónevének kiíratása.
     */
    public void setLoggedUserUsername() {
        loggedUserUsername.setText(user.getUserName());
    }

    /**
     * Bejelentkezett felhasználó admin jogának kiíratása.
     */
    public void setLoggedUserAdmin() {
        loggedUserAdmin.setText(user.getAdmin() ? "Igen" : "Nem");
    }

}
