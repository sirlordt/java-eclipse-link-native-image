package com.jpa.example.models;

import java.util.HashMap;
import java.util.List;
// import java.util.HashSet;
// import java.util.Map;
// import java.util.Set;

import com.jpa.example.converter.JpaConverterJson;

//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map;

//import com.fasterxml.jackson.annotation.JsonValue;

//import java.util.Map;

//import com.fasterxml.jackson.databind.JsonNode;
// import com.jpa.example.converter.JpaConverterJson;

// import jakarta.json.Json;
// import jakarta.json.JsonObject;
//import jakarta.json.JsonValue;
//import jakarta.json.JsonValue;
//import jakarta.json.JsonValue;
//import jakarta.json.JsonObject;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table( name = "Author" )
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="Id",nullable = false)
    public String Id;

    @Column(name="Name", nullable = false, unique = true)
    @Size(max = 100)
    public String Name;

    @Column(name="BirthYear", nullable = false)
    public Integer BirthYear;

    @Column(name="ExtraData", nullable = true)
    @Convert(converter = JpaConverterJson.class) //, attributeName = "ExtraData")
    //@ElementCollection
    public HashMap<String,Object> ExtraData = null; // = new HashMap<>(); 

    // @OneToMany //(targetEntity = Book.class, mappedBy="Author")
    // @JoinTable( name="Book", 
    //             joinColumns=@JoinColumn(name="Id", referencedColumnName="AuthorId"),
    //             inverseJoinColumns=@JoinColumn(name="AuthorId"))    
    //@JoinColumn(name = "AuthorId")
    @OneToMany( mappedBy = "Author" ) //,cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //@JoinColumn(name = "AuthorId")
    //public Set<Book> books = new HashSet<>();    
    public List<Book> bookList;

    // public Map<String, Object> getExtraData() {
    //     return ExtraData;
    // }

    // public void setExtraData(Map<String, Object> extraData) {
    //     ExtraData = extraData;
    // }

    // public List<Book> getBookList() {
    //     return bookList;
    // }

    // public void setBookList(List<Book> bookList) {
    //     this.bookList = bookList;
    // }

    public Author() {}

    @Override
    public String toString () {

        //var bookList = this.getBookList();

        return "Author{ Id='" + Id + "', Name='" + Name + "', BirdYear='" + BirthYear + "', ExtraData='" + ( ExtraData != null ? ExtraData.toString(): null ) + "' }";

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

