package model;

import javax.persistence.*;
import javax.xml.bind.ValidationException;
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
    private boolean is_completed;

    /** Дата и время регистрации*/
    @Column(updatable = false)
    private LocalDateTime dt_created;

    /** Дата и время выполнения */
    @Column
    private LocalDateTime dt_completed;

    /**
     * Метод автоматического добавления даты и времени создания
     */
    @PostPersist
    protected void onCreate() {
        dt_created = LocalDateTime.now();
        this.order = (int) this.id;
    }

    public Task() {
    }

    public Task(String title) throws ValidationException {
        if (Optional.ofNullable(title).orElse("").equals("")) {
            throw new ValidationException("Название дела не может быть пустым!");
        }
        this.title = title;
        this.is_completed = false;
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

    public boolean isIs_completed() {
        return is_completed;
    }

    public LocalDateTime getDt_created() {
        return dt_created;
    }

    public LocalDateTime getDt_completed() {
        return dt_completed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setIs_completed(boolean is_completed) {
        if (!this.is_completed && is_completed)
            dt_completed = LocalDateTime.now();
        if (this.is_completed && !is_completed)
            dt_completed = null;
        this.is_completed = is_completed;
    }
}
