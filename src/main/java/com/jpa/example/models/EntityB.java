package com.jpa.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "EntityB" )
public class EntityB {
    @Id
    @GeneratedValue
    private int myIdB;
    private String strB;
    @ManyToOne
    private EntityA refEntityA;

    public String getStrB() {
        return strB;
    }

    public void setStrB(String strB) {
        this.strB = strB;
    }

    public EntityA getRefEntityA() {
        return refEntityA;
    }

    public void setRefEntityA(EntityA refEntityA) {
        this.refEntityA = refEntityA;
    }

    @Override
    public String toString() {
        return "EntityB{" +
                "myIdB=" + myIdB +
                ", strB='" + strB + '\'' +
                '}';
    }
}