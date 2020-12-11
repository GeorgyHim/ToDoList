package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Список дел
 */
@Entity
@Table(name="todolist")
public class ToDoList implements Serializable {
}
