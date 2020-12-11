package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ToDoList> toDoLists;

    /**
     * Метод автоматического добавления даты и времени регистрации
     */
    @PostPersist
    protected void onCreate() {
        dtRegistered = LocalDate.now();
    }

    protected User(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = Optional.ofNullable(name).orElse("");
        this.surname = Optional.ofNullable(surname).orElse("");
        this.toDoLists = Collections.emptyList();
    }

    protected User(String email, String password) {
        this(email, password, "", "");
    }

    protected User(String email) {
        this(email, "secret");
    }

    public User() {
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

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }
}
