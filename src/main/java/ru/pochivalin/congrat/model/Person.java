package ru.pochivalin.congrat.model;

import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Access;
import javax.persistence.AccessType;
import java.io.Serializable;
import java.util.Date;

@Data //constructor without arguments, with arguments, all getters, all setters
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
