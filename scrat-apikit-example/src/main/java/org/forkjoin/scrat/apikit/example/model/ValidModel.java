package org.forkjoin.scrat.apikit.example.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ValidModel {
    @NotEmpty
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ValidModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
