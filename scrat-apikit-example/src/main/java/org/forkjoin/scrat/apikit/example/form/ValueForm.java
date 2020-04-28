package org.forkjoin.scrat.apikit.example.form;

import lombok.Data;

@Data
public class ValueForm<T> {
    private T value;
}
