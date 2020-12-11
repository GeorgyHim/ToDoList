package model;

import util.exception.ValidationError;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Задача
 */
@Entity
@Table(name = "task")
public class Task {
    private static final long serialVersionUID = 1;

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Название */
    @Column(nullable = false)
    private String title;

    /** Номер дела в списке */
    @Column
    private int order;

    /** Выполнено ли дело */
    @Column
    private boolean completed;

    /** Список, к которому относится дело */
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "list_id")
    private ToDoList list;

    /** Дата и время регистрации*/
    @Column(updatable = false)
    private LocalDateTime dtCreated;

    /** Дата и время выполнения */
    @Column
    private LocalDateTime dtCompleted;

    /**
     * Метод автоматического добавления даты и времени создания
     */
    @PostPersist
    protected void onCreate() {
        dtCreated = LocalDateTime.now();
        this.order = (int) this.id;
    }

    public Task() {
    }

    protected Task(String title, ToDoList list) throws ValidationError {
        if (Optional.ofNullable(title).orElse("").equals("")) {
            throw new ValidationError("Название дела не может быть пустым!");
        }
        if (list == null) {
            throw new ValidationError("Укажите родительский список дел!");
        }
        this.title = title;
        this.completed = false;
        this.list = list;
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

    public int getOrder() {
        return order;
    }



    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getDtCreated() {
        return dtCreated;
    }

    public LocalDateTime getDtCompleted() {
        return dtCompleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setCompleted(boolean completed) {
        if (!this.completed && completed)
            dtCompleted = LocalDateTime.now();
        if (this.completed && !completed)
            dtCompleted = null;
        this.completed = completed;
    }
}
