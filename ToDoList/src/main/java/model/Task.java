package model;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private boolean is_completed = false;

    /** Дата и время регистрации*/
    @Column(updatable = false)
    private LocalDateTime dt_created;

    /** Дата и время выполнения */
    @Column
    private LocalDateTime dt_completed;

    /**
     * Метод автоматического добавления даты и времени создания
     */
    @PrePersist
    protected void onCreate() {
        dt_created = LocalDateTime.now();
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
}
