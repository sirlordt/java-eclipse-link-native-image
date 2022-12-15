package com.jpa.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity( name = "MyObject" )
@Table( name = "MyObject" )
public class MyObject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String Id;
    
    public String Data;

    // public String getId() {
        
    //     return Id;

    // }

    // public void setId( String id ) {
        
    //     this.Id = id;

    // }

    // public String getData() {
        
    //     return Data;

    // }

    // public void setData( String strData ) {

    //     this.Data = strData;

    // }

    @Override
    public String toString () {

        return "MyObject{ Id='" + Id + "', Data='" + Data + "' }";

    }

}