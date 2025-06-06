package com.example.volkov_av;
import java.io.Serializable;

public class MyObject implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public MyObject(String name) {
        this.name = name;
    }

}
