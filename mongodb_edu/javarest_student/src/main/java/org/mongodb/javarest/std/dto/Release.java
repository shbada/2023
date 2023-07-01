package org.mongodb.javarest.std.dto;

import lombok.Data;

@Data
public class Release {

    public Integer version;
    public String date;

    public Release() {

    }

    public Release(Integer version, String date) {
        this.version = version;
        this.date = date;
    }

}
