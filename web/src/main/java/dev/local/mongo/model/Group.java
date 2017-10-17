package dev.local.mongo.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class Group {
    @Id
    private String newId;
    private int count;
    private int totalAge;

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalAge() {
        return totalAge;
    }

    public void setTotalAge(int totalAge) {
        this.totalAge = totalAge;
    }

    @Override
    public String toString() {
        return "create: "+ newId + ", count: "+ count + ", totalAge: "+ totalAge;
    }
}
