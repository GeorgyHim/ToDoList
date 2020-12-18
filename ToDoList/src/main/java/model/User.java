package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import util.exception.ValidationError;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * Пользователь
 */
@Entity
@Table(name = "profile")
public class User implements Serializable {
    private static final long serialVersionUID = 2;

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Email */
    @Column(unique = true, updatable = false, nullable = false)
    private String email;

    /** Пароль в виде хэша */
    @Column
    private String password;

    /** Имя  */
    @Column(nullable = false)
    private String name;

    /** Фамилия */
    @Column(nullable = false)
    private String surname;

    /** Дата регистрации*/
    @Column(updatable = false)
    private LocalDate dtRegistered;

    /** Списки дел*/
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<ToDoList> toDoLists;

    /**
     * Метод автоматического добавления даты и времени регистрации
     */
    @PrePersist
    protected void onCreate() {
        dtRegistered = LocalDate.now();
    }

    protected User(String email, String password, String name, String surname) throws ValidationError {
        if (Optional.ofNullable(email).orElse("").equals(""))
            throw new ValidationError("Email не может быть пустым!");
        if (Optional.ofNullable(password).orElse("").equals(""))
            throw new ValidationError("Пароль не может быть пустым!");

        this.email = email;
        this.password = password;
        this.name = Optional.ofNullable(name).orElse("");
        this.surname = Optional.ofNullable(surname).orElse("");
        this.toDoLists = new HashSet<>();
    }

    protected User(String email, String password) throws ValidationError {
        this(email, password, "", "");
    }

    protected User(String email) throws ValidationError {
        this(email, "secret");
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDtRegistered() {
        return dtRegistered;
    }

    public Set<ToDoList> getToDoLists() {
        return toDoLists;
    }
}
