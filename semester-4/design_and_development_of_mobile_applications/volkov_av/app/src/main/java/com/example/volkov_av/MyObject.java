package com.example.volkov_av;
import java.io.Serializable;

public class MyObject implements Serializable {
    private String name;
    private String group;
    private String are;
    private String mark;

    public String getAge() {
        return are;
    }

    public String getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public MyObject(String name, String group, String are, String mark) {
        this.name = name;
        this.group = group;
        this.are = are;
        this.mark = mark;
    }

}
