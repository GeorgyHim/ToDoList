package model;

import javax.persistence.*;
import java.io.Serializable;

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


}
