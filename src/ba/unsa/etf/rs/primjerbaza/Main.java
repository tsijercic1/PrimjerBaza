package ba.unsa.etf.rs.primjerbaza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        launch(args);
        UserDAO dao = UserDAO.getInstance();
        dao.adduserToDatabase(new User("Test", "Testovic"));
        dao.adduserToDatabase(new User("Test2", "Testovic"));
        dao.adduserToDatabase(new User("Test3", "Testovic"));
        ArrayList<User> users = dao.getUsersFromDatabase();
        for (User user : users) {
            System.out.println(user);
        }
    }
}
