package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Пользователь
 */
@Entity
@Table
public class User implements Serializable {
    private static final long serialVersionUID = 1;

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
    private String first_name;

    /** Фамилия */
    @Column(nullable = false)
    private String last_name;

    /** Дата и время регистрации*/
    @Column
    private LocalDateTime dt_registered;

    /**
     * Метод автоматического добавления даты и времени регистрации
     */
    @PrePersist
    protected void onCreate() {
        dt_registered = LocalDateTime.now();
    }

    public User(String email, String password, String first_name, String last_name) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public LocalDateTime getDt_registered() {
        return dt_registered;
    }
}
