package com.jpa.example.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table( name = "EntityA" )
public class EntityA {
    @Id
    @GeneratedValue
    private int myIdA;
    private String strA;
    @OneToMany(mappedBy = "refEntityA")
    private List<EntityB> entityBList;

    public String getStrA() {
        return strA;
    }

    public void setStrA(String strA) {
        this.strA = strA;
    }

    public List<EntityB> getEntityBList() {
        return entityBList;
    }

    public void setEntityBList(List<EntityB> entityBList) {
        this.entityBList = entityBList;
    }

    @Override
    public String toString() {
        return "EntityA{" +
                "myIdA=" + myIdA +
                ", strA='" + strA + '\'' +
                '}';
    }
}