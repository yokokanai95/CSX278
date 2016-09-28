package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PreferedContact.
 */
@Entity
@Table(name = "prefered_contact")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PreferedContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name_of_choice", nullable = false)
    private String nameOfChoice;

    @OneToMany(mappedBy = "preferedcontact")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> students = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfChoice() {
        return nameOfChoice;
    }

    public PreferedContact nameOfChoice(String nameOfChoice) {
        this.nameOfChoice = nameOfChoice;
        return this;
    }

    public void setNameOfChoice(String nameOfChoice) {
        this.nameOfChoice = nameOfChoice;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public PreferedContact students(Set<Student> students) {
        this.students = students;
        return this;
    }

    public PreferedContact addStudent(Student student) {
        students.add(student);
        student.setPreferedcontact(this);
        return this;
    }

    public PreferedContact removeStudent(Student student) {
        students.remove(student);
        student.setPreferedcontact(null);
        return this;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreferedContact preferedContact = (PreferedContact) o;
        if(preferedContact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, preferedContact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PreferedContact{" +
            "id=" + id +
            ", nameOfChoice='" + nameOfChoice + "'" +
            '}';
    }
}
