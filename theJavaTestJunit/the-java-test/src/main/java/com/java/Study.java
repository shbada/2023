package com.java;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Study {
    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;

    private String name;

    public Study(int limit, String name) {
        this.limit = limit;
        this.name = name;
    }

    public Study(int limit) {
        this.limit = limit;
    }


    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }
}
