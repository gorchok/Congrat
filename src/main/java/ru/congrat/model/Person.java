package ru.congrat.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data //конструктор без аргументов, со всеми аргументами, все гетеры и сетеры
@Entity
@Table(name = "person")
@Access(AccessType.FIELD)
public class Person implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firsName;
    private String lastName;
    private String type;
    private String email;
    @Temporal(TemporalType.DATE)
    private Date date;
}
