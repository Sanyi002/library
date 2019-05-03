package hu.unideb.inf.library.controller;

import hu.unideb.inf.library.model.pojo.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

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
    }
}
