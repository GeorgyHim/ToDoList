package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import util.exception.ValidationError;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Список дел
 */
@Entity
@Table(name="todolist", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "user_id"})})
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
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    /** Задачи списка*/
    @OneToMany(mappedBy = "list", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("orderNumber DESC")
    @JsonManagedReference
    private Set<Task> tasks;

    public ToDoList() {
    }

    protected ToDoList(String title, User user) throws ValidationError {
        if (Optional.ofNullable(title).orElse("").equals("")) {
            throw new ValidationError("Название списка не может быть пустым!");
        }
        if (user == null) {
            throw new ValidationError("Укажите создателя списка");
        }
        if (user.getToDoLists().stream().anyMatch(list -> list.getTitle().equals(title))) {
            throw new ValidationError("Список с указанным именем уже существует");
        }
        this.title = title;
        this.user = user;
        this.tasks = new HashSet<>();
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

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
