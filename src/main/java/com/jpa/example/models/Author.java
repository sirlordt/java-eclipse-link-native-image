package com.jpa.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table( name = "Author" )
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="Id")
    public String Id;

    @Column(name="Name", nullable = false, unique = true)
    @Size(max = 100)
    public String Name;

    @Column(name="BirthYear", nullable = false)
    public Integer BirthYear;

    public Author() {}

    @Override
    public String toString () {

        return "Author{ Id='" + Id + "', Name='" + Name + "', BirdYear='" + BirthYear + "' }";

    }

    // public Author(String name, Integer birthYear) {
    //     this.Name = name;
    //     this.BirthYear = birthYear;
    // }

    // public String getId() {
    //     return Id;
    // }

    // public void setId(String Id) {
    //     this.Id = Id;
    // }

    // public String getName() {
    //     return Name;
    // }

    // public void setName(String name) {
    //     this.Name = name;
    // }

    // public Integer getBirthYear() {
    //     return BirthYear;
    // }

    // public void setBirthYear(Integer birthYear) {
    //     this.BirthYear = birthYear;
    // }
}

