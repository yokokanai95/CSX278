package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "email_address", nullable = false)
    private String emailAddress;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "graduation_date")
    private LocalDate graduationDate;

    @ManyToOne
    private PreferedContact preferedcontact;

    @ManyToOne
    private School school;

    @ManyToOne
    private Dormitory dorm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public Student fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Student emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Student phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public Student graduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
        return this;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public PreferedContact getPreferedcontact() {
        return preferedcontact;
    }

    public Student preferedcontact(PreferedContact preferedContact) {
        this.preferedcontact = preferedContact;
        return this;
    }

    public void setPreferedcontact(PreferedContact preferedContact) {
        this.preferedcontact = preferedContact;
    }

    public School getSchool() {
        return school;
    }

    public Student school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Dormitory getDorm() {
        return dorm;
    }

    public Student dorm(Dormitory dormitory) {
        this.dorm = dormitory;
        return this;
    }

    public void setDorm(Dormitory dormitory) {
        this.dorm = dormitory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        if(student.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", fullName='" + fullName + "'" +
            ", emailAddress='" + emailAddress + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", graduationDate='" + graduationDate + "'" +
            '}';
    }
}
