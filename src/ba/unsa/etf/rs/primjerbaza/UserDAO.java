package ba.unsa.etf.rs.primjerbaza;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDAO {
    private static UserDAO instance;
    private static Connection connection;

    private static PreparedStatement addUser;
    private static PreparedStatement updateUser;
    private static PreparedStatement deleteUser;
    private static PreparedStatement getUsers;

    private UserDAO(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:baza.db");
            initializeStatements();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                setupDatabase();
                initializeStatements();
            } catch (SQLException ex) {

            }
        }
    }

    private void setupDatabase() {
        String sql="";
        URL x = getClass().getResource("/setupDatabase.sql");
        FileReader fileReader =
                null;
        try {
            fileReader = new FileReader(x.getFile());

        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader =
                new BufferedReader(fileReader);
            Scanner scanner = new Scanner(bufferedReader);
        while(scanner.hasNextLine()){
            sql+=scanner.nextLine();
        }
            try {
                scanner.close();
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sql = sql.replace("\n"," ");
        sql = sql.replace(";","\n");
        String[] upiti = sql.split("\n");
        try {
            Statement statement = connection.createStatement();
            for (String upit : upiti){
                statement.execute(upit);
            }
        } catch (SQLException e) {

        }
    }

    private void initializeStatements() throws SQLException {
        addUser = connection.prepareStatement("insert into users values(?,?,?,?)");
        updateUser = connection.prepareStatement("update users set name=?,surname=?,birthday=? where id=?");
        deleteUser = connection.prepareStatement("delete from users where id = ?");
        getUsers = connection.prepareStatement("select * from users");
    }

    public static UserDAO getInstance(){
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public ArrayList<User> getUsersFromDatabase(){
        ArrayList<User> users = new ArrayList<User>();
        try {
            ResultSet resultSet = getUsers.executeQuery();
            User user;
            while(    (user = getUserFromResultSet(resultSet))!=null          ){
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {


        }
        return users;
    }

    public void adduserToDatabase(User user) {
        int id = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT max(id)+1 from users");
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {

        }
        user.setId(id);

        try {
            addUser.setInt(1,id);
            addUser.setString(2, user.getName());
            addUser.setString(3, user.getSurname());
            addUser.setDate(4,Date.valueOf(user.getBirthday()));
            addUser.executeUpdate();
        } catch (SQLException e) {


        }

    }

    private User getUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                LocalDate birthday = resultSet.getDate("birthday").toLocalDate();
                user = new User(name, surname,birthday);
                user.setId(id);
            }
        } catch (SQLException e) {


        }
        return user;
    }


}
