package ba.unsa.etf.rs.primjerbaza;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class User {
    private String name,surname;
    private LocalDate birthday;
    private int id;

    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
        birthday = LocalDate.now();
    }

    public User(String name, String surname, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return name+" "+surname+" "+birthday.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }




}
