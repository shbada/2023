package org.mongodb.javarest.std.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Review {
    public String name;
    public String type;
    public String userid;

    //@JsonProperty("txt")
    public String comment;

    public Date createDate;

    public Review() {

    }

    public Review(String userid,String comment) {
        this.userid = userid;
        this.comment = comment;
    }

    public Review(String userid,String comment, Date createDate) {
        this.userid = userid;
        this.comment = comment;
        this.createDate = createDate;
    }

    public Review(String name,String type,String userid,String comment) {
        this.name = name;
        this.type = type;
        this.userid = userid;
        this.comment = comment;
    }

    public Review(String name,String type,String userid,String comment, Date createDate) {
        this.name = name;
        this.type = type;
        this.userid = userid;
        this.comment = comment;
        this.createDate = createDate;
    }

}
