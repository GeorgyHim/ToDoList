package model;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Список дел
 */
@Entity
@Table(name="todolist")
public class ToDoList implements Serializable {
    private static final long serialVersionUID = 2;

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Название */
    @Column(nullable = false)
    private String title;

    /** Пользователь, которому принадлежит список дел */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    /** Задачи списка*/
    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER)
    private List<Task> tasks;

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
        this.tasks = new ArrayList<>();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
