package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
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

    /** Дата и время регистрации*/
    @Column
    private LocalDate dt_registered;

    /** Списки дел*/
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    public Collection<ToDoList> lists;

    /**
     * Метод автоматического добавления даты и времени регистрации
     */
    @PrePersist
    protected void onCreate() {
        dt_registered = LocalDate.now();
    }

    public User(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = Optional.ofNullable(name).orElse("");
        this.surname = Optional.ofNullable(surname).orElse("");
    }

    public User(String email, String password) {
        this(email, password, "", "");
    }

    public User(String email) {
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

    public LocalDate getDt_registered() {
        return dt_registered;
    }
}
