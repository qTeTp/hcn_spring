package com.hcn.hcn.model;

import jakarta.persistence.*;

@Entity
@Table(name = "header")
public class ChatHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String from_id;
    private String to_id;
    private String last_message;
    private String last_id;
    private String time;

    private Float all_points = 0.0f; // 보내야 할 총 금액
    private Float sended_points = 0.0f; // 송금 금액 필드

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getLast_message() {
        return last_message;
    }

    public void setLast_message(String last_message) {
        this.last_message = last_message;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getAll_points() {
        return all_points;
    }

    public void setAll_points(Float all_points) {
        this.all_points = all_points;
    }

    public Float getSended_points() {
        return sended_points;
    }

    public void setSended_points(Float sended_points) {
        this.sended_points = sended_points;
    }
}
