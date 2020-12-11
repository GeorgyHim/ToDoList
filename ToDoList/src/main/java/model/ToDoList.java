package model;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.util.Optional;

/**
 * Список дел
 */
@Entity
@Table(name="todolist")
public class ToDoList implements Serializable {
    private static final long serialVersionUID = 1;

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Название списка*/
    @Column(nullable = false)
    private String title;

    /** Пользователь, которому принадлежит список дел */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public ToDoList() {
    }

    public ToDoList(String title, User user) throws ValidationException {
        if (Optional.ofNullable(title).orElse("").equals("")) {
            throw new ValidationException("Название списка не может быть пустым!");
        }
        if (user == null) {
            throw new ValidationException("Укажите создателя списка");
        }
        this.title = title;
        this.user = user;
    }
}
