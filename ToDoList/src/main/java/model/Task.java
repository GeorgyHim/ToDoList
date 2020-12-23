package model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import model.helper.DateGroup;
import util.exception.ValidationError;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Задача
 */
@Entity
@Table(name = "task")
public class Task {
    private static final long serialVersionUID = 1;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /** Идентификатор */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /** Название */
    @Column(nullable = false)
    private String description;

    /** Номер дела в списке */
    @Column
    private int orderNumber;

    /** Выполнено ли дело */
    @Column
    private boolean completed;

    /** Список, к которому относится дело */
    @ManyToOne(optional = false)
    @JoinColumn(name = "list_id")
    @JsonBackReference
    private ToDoList list;

    /** Дата и время регистрации*/
    @Column(updatable = false)
    private LocalDateTime dtCreated;

    /** Дата и время выполнения */
    @Column
    private LocalDateTime dtCompleted;

    /** Планируемая дата выполнения */
    private DateGroup dateGroup;

    /**
     * Метод автоматического добавления даты и времени создания и планирования
     */
    @PrePersist
    protected void onCreate() {
        dtCreated = LocalDateTime.now();
    }


    public Task() {
    }

    protected Task(String description, ToDoList list) throws ValidationError {
        if (Optional.ofNullable(description).orElse("").equals("")) {
            throw new ValidationError("Описание дела не может быть пустым!");
        }
        if (list == null) {
            throw new ValidationError("Укажите родительский список дел!");
        }
        this.description = description;
        this.completed = false;
        this.list = list;
        this.dateGroup = DateGroup.TODAY;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public ToDoList getList() {
        return list;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getDtCreated() {
        return dtCreated.format(formatter);
    }

    public String getDtCompleted() {
        if (dtCompleted == null)
            return "";
        return dtCompleted.format(formatter);
    }

    public DateGroup getDateGroup() {
        return dateGroup;
    }

    public void setDateGroup(DateGroup dateGroup) {
        this.dateGroup = dateGroup;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCompleted() {
        if (!this.completed)
            dtCompleted = LocalDateTime.now();
        if (this.completed)
            dtCompleted = null;
        this.completed = !this.completed;
    }
}
